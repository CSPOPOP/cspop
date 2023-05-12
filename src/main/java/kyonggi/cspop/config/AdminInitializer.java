package kyonggi.cspop.config;

import kyonggi.cspop.application.util.crypto.PasswordEncoder;
import kyonggi.cspop.domain.admins.Admins;
import kyonggi.cspop.domain.admins.repository.AdminsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    public static final int NO_ADMINS = 0;

    private final AdminsRepository adminsRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {

        if (adminsRepository.findAll().size() == NO_ADMINS) {
            Admins admin1 = Admins.createAdmins("관리자1", "admin1", "cspopadmin");
            Admins admin2 = Admins.createAdmins("관리자2", "admin2", "cspopadmin");
            Admins admin3 = Admins.createAdmins("관리자3", "admin3", "cspopadmin");
            Admins admin4 = Admins.createAdmins("관리자4", "admin4", "cspopadmin");
            Admins admin5 = Admins.createAdmins("관리자5", "admin5", "cspopadmin");
            List<Admins> admins = Arrays.asList(admin1, admin2, admin3, admin4, admin5);
            admins.stream().forEach(admin -> encryptPassword(admin));

            adminsRepository.saveAll(admins);
        }
    }

    private void encryptPassword(Admins admin) {
        String encryptPassword = passwordEncoder.encryptPassword(admin.getAdminPassword());
        admin.encryptPassword(encryptPassword);
    }
}
