
package com.example.demo.DTO.response;

import java.util.List;
import com.example.demo.entities.TipoInstitucion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoInstitucionDTO  implements IResponseDTO<TipoInstitucion>{
    private Long id;
    private String nombre;
    private List<InstitucionDTO> instituciones;

    @Override
    public void parseFromEntity(TipoInstitucion entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.instituciones = entity.getInstituciones().stream()
                .map(institucion -> {
                    InstitucionDTO institucionDTO = new InstitucionDTO();
                    institucionDTO.parseFromEntity(institucion);
                    return institucionDTO;
                })
                .toList();
    }
}
