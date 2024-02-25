package org.dmiit3iy.model;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserFile {

    private long id;
    @NonNull
    private String filename;
    @NonNull
    private String serverFilename;
    @NonNull
    private int version;

}
