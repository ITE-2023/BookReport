package com.project.bookreport.domain;

import com.project.bookreport.domain.base.BaseEntity;
import com.project.bookreport.domain.status.MemberRole;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Member extends BaseEntity {

    @Column(length = 20)
    private String username;

    private String password;

    /*
    @ElementCollection : 컬렉션의 각 요소를 저장할 수 있다. 부모 Entity와 독립적으로 사용 X
    @CollectionTable : @ElementCollection과 함께 사용될 때, 생성될 테이블의 이름 지정
    */
    @ElementCollection(targetClass = MemberRole.class)
    @CollectionTable(name = "member_roles",
        joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roleSet;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;
}