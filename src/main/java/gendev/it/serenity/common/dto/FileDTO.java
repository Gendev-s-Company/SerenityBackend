package gendev.it.serenity.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private String id;
    private String type;
    private String nameFile;
    byte[] data;
}
