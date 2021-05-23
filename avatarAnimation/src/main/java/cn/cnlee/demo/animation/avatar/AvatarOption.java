package cn.cnlee.demo.animation.avatar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Accessors(prefix = "m")
public class AvatarOption {
    private String mActionId;
    private String mAvatarName;
    private int mDisplayId;
    private int mAvatarType;
}
