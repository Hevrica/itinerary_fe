package itinerary.itinerary_fe.dto;

import java.util.Set;

import itinerary.itinerary_fe.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private String username;
	private String password;
	private String nickname;
	private Set<Authority> authorities;
}