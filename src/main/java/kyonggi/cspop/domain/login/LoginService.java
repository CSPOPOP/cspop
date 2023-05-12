package kyonggi.cspop.domain.login;

import kyonggi.cspop.application.util.crypto.PasswordEncoder;
import kyonggi.cspop.domain.admins.Admins;
import kyonggi.cspop.domain.admins.repository.AdminsRepository;
import kyonggi.cspop.domain.login.dto.UserSessionDto;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.repository.UsersRepository;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final AdminsRepository adminsRepository;

    public UserSessionDto login(String loginId, String loginPassword) {
        Users findUser = usersRepository.findByStudentId(loginId)
                .orElseThrow(() -> new CsPopException(CsPopErrorCode.USER_NOT_FOUND));
        isCorrectStudentPassword(loginPassword, findUser);
        return new UserSessionDto(findUser);
    }

    private void isCorrectStudentPassword(String loginPassword, Users findUser) {
        if (!passwordEncoder.isMatch(loginPassword, findUser.getStudentPassword())) {
            throw new CsPopException(CsPopErrorCode.WRONG_PASSWORD);
        }
    }

    public UserSessionDto loginAsAdmin(String loginId, String loginPassword){
        Optional<Admins> findAdmin = adminsRepository.findByAdminId(loginId);
        if (findAdmin.isPresent()) {
            if (isCorrectAdminPassword(loginPassword, findAdmin)) return new UserSessionDto(findAdmin.get());
        }
        return new UserSessionDto();
    }

    private boolean isCorrectAdminPassword(String loginPassword, Optional<Admins> findAdmin) {
        if(!passwordEncoder.isMatch(loginPassword, findAdmin.get().getAdminPassword())) {
            return false;
        }
        return true;
    }
}
