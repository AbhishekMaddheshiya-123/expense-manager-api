package in.expansetrackerapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse   //  2nd step:   we are creating jwt response  model class to hold the jwt token s onces the we ctreate the jwt token so we need to send the response to the client
{
    private final String jwtToken;
}
