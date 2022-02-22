package backend.core.global.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    // 클라이언트 upload 시 저장한 이름
    private String uploadFileName;

    // 서버에 저장된 이름
    private String storeFileName;

    @Builder
    public Profile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
