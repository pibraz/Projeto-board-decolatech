package br.com.bio;

import br.com.bio.persistence.migration.MigrationStrategy;
import br.com.bio.ui.BoardMenu;
import br.com.bio.ui.MainMenu;

import java.sql.SQLException;

import static br.com.bio.persistence.config.ConnectionConfig.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();

    }
}