package kyonggi.cspop.domain.users.service;

import kyonggi.cspop.application.controller.users.dto.UserPasswordRequestDto;
import kyonggi.cspop.application.util.crypto.BCryptoPasswordEncoder;
import kyonggi.cspop.application.util.crypto.PasswordEncoder;
import kyonggi.cspop.domain.form.finalform.repository.FinalFormRepository;
import kyonggi.cspop.domain.form.interimform.repository.InterimFormRepository;
import kyonggi.cspop.domain.form.otherform.repository.OtherFormRepository;
import kyonggi.cspop.domain.form.proposalform.repository.ProposalFormRepository;
import kyonggi.cspop.domain.form.submitform.repository.SubmitFormRepository;
import kyonggi.cspop.domain.users.Users;
import kyonggi.cspop.domain.users.repository.UsersRepository;
import kyonggi.cspop.exception.CsPopErrorCode;
import kyonggi.cspop.exception.CsPopException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubmitFormRepository submitFormRepository;
    private final ProposalFormRepository proposalFormRepository;
    private final InterimFormRepository interimFormRepository;
    private final FinalFormRepository finalFormRepository;
    private final OtherFormRepository otherFormRepository;


    @Transactional
    public Long saveUser(Users user) {
        encryptPassword(user);
        Users saveUser = usersRepository.save(user);
        return saveUser.getId();
    }

    private void encryptPassword(Users user) {
        String encryptPassword = passwordEncoder.encryptPassword(user.getStudentPassword());
        user.encryptPassword(encryptPassword);
    }

    public void checkDuplicateStudentNumber(String studentId) {
        if (usersRepository.existsByStudentId(studentId)) {
            throw new CsPopException(CsPopErrorCode.DUPLICATE_STUDENT_ID);
        }
    }

    public Users findUserByStudentId(String studentId) {
        Users users = usersRepository.findByStudentId(studentId).get();
        if (!Objects.isNull(users.getSubmitForm())) {
            users.getSubmitForm().getStudentId();
        }

        if (!Objects.isNull(users.getProposalForm())) {
            users.getProposalForm().getStudentId();
        }

        if (!Objects.isNull(users.getInterimForm())) {
            users.getInterimForm().getCreatedDate();
        }

        if (!Objects.isNull(users.getFinalForm())) {
            users.getFinalForm().getCreatedDate();
        }

        if (!Objects.isNull(users.getOtherForm())) {
            users.getOtherForm().getCreatedDate();
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void checkExistStudentNumber(String studentId) {
        if (!usersRepository.existsByStudentId(studentId)) {
            throw new CsPopException(CsPopErrorCode.USER_NOT_FOUND);
        }
    }

    public void checkExistPasswordAnswer(String answerPw) {
        if (!usersRepository.existsUsersByAnswerPw(answerPw)) {
            throw new CsPopException(CsPopErrorCode.ANSWER_PASSWORD_NOT_FOUND);
        }
    }

    @Transactional
    public void updatePassword(String studentId, UserPasswordRequestDto userPasswordRequestDto) {
        Users users = usersRepository.findByStudentId(studentId).get();
        BCryptoPasswordEncoder encoder = new BCryptoPasswordEncoder();
        String securePw = encoder.encryptPassword(userPasswordRequestDto.getStudentPassword());
        users.updatePassword(securePw);
    }

    @Transactional
    public void updateUserBySubmitForm(Long userId, Long submitFormId) {
        Users user = usersRepository.findById(userId).get();
        user.addSubmitForms(submitFormRepository.findById(submitFormId).get());
    }

    @Transactional
    public void updateUserByProposalForm(Long userId, Long proposalFormId) {
        Users user = usersRepository.findById(userId).get();
        user.addProposalForms(proposalFormRepository.findById(proposalFormId).get());
    }

    @Transactional
    public void updateUserByInterimForm(Long userId, Long interimFormId) {
        Users user = usersRepository.findById(userId).get();
        user.addInterimForms(interimFormRepository.findById(interimFormId).get());
    }

    @Transactional
    public void updateUserByFinalForm(Long userId, Long finalFormId) {
        Users user = usersRepository.findById(userId).get();
        user.addFinalForms(finalFormRepository.findById(finalFormId).get());
    }

    @Transactional
    public void updateUserByOtherForm(Long userId, Long otherFormId) {
        Users user = usersRepository.findById(userId).get();
        user.addOtherForms(otherFormRepository.findById(otherFormId).get());
    }
}