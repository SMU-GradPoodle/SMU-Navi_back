package smu.poodle.smnavi.tipoff.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import smu.poodle.smnavi.tipoff.domain.Location;
import smu.poodle.smnavi.tipoff.domain.TipOff;
import smu.poodle.smnavi.map.domain.data.TransitType;
import smu.poodle.smnavi.tipoff.domain.Kind;
import smu.poodle.smnavi.user.domain.UserEntity;
import smu.poodle.smnavi.user.sevice.LoginService;

@Getter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TipOffRequestDto {
    LoginService loginService;
    String transitType;
    int kind;
    String stationId;

    String author;

    @Length(min = 4, max = 4, message = "비밀번호는 4자로 입력해주세요")
    Long pw;

    @NotEmpty(message = "내용은 필수 입력 항목 입니다.")
    @Length(min = 1, max = 200, message = "내용은 10자 이상 5000자 이하로 입력해주세요.")
    String content;

    public TipOff ToEntity(Long loginUserId) {
        return TipOff.builder()
                .author(loginUserId != 0 ? UserEntity.builder().id(loginUserId).build() : null)
                .content(content)
                .transitType(TransitType.valueOf(transitType))
                .kind(Kind.getKindByNumber(this.kind))
                .location(Location.stationIdToLocation(stationId))
                .build();
    }
    public boolean isPasswordRequired() { //로그인 한 경우
        return !loginService.isLogIn(); //비밀번호 필요 없음면 flase
    }
}


