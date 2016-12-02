package votes.jani.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import votes.jani.domain.User;
import votes.jani.domain.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	private final UserRepository repository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

    @Override
    public UserDetails loadUserByUsername(String theUsersName) throws UsernameNotFoundException {   
    	User currentUser = repository.findByUsername(theUsersName);
        UserDetails user = new org.springframework.security.core.userdetails.User(	theUsersName,
        																			currentUser.getPasswordHash(),
        																			AuthorityUtils.createAuthorityList(currentUser.getRole()));
        return user;
    }   
} 
