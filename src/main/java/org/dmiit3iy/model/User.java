package org.dmiit3iy.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    private long id;
    @NonNull
    private String fio;
    @NonNull
    private String login;
    @NonNull
    private String password;
}
