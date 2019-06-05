
package forms;

import java.util.Collection;

import security.Authority;

public class UserAccountForm {

	private int						id;
	private String					username;
	private String					password;
	private String					confirmPassword;
	private Collection<Authority>	authorities;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Collection<Authority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(final Collection<Authority> authorities) {
		this.authorities = authorities;
	}

}
