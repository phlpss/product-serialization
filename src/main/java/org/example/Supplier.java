package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private String companyName;

    @JsonIgnore // Jackson will ignore this field in JSON serialization
    private transient Date contractStartDate;
}