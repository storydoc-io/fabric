package io.storydoc.fabric.jdbc.connection;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDBCConnectionDetails  {

    private String userName;

    private String password;

    private String jdbcUrl;


}
