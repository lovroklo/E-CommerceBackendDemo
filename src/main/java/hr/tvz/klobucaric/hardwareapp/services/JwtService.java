package hr.tvz.klobucaric.hardwareapp.services;


import hr.tvz.klobucaric.hardwareapp.domain.User;

public interface JwtService {

    boolean authenticate(String token);
    String createJwt(User user);

}
