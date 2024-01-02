package edu.neu.csye6200.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_group")
public class UserGroup implements Serializable {

    @Embeddable
    @Setter
    @Getter
    public static class UserGroupId implements Serializable {
        private int userId;
        private int groupId;
    }

    @EmbeddedId
    private UserGroupId userGroupId;
}
