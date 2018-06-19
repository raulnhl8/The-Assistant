package com.example.pm.assistant.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class Database_Impl extends Database {
  private volatile Dao _dao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Contato` (`contato_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `contato_nome` TEXT, `contato_relacionamento` TEXT, `contato_foto` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Cuidador` (`cuidador_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cuidador_email` TEXT, `cuidador_senha` TEXT, `cuidador_celular` TEXT, `cuidador_endereco` TEXT, `contato_id` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Usuario` (`usuario_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `usuario_nome` TEXT, `usuario_sexo` INTEGER NOT NULL, `usuario_dataDeNascimento` TEXT, `dicaAtiv` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"04ee1ae24fd32329b0d3391b13b1bd68\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Contato`");
        _db.execSQL("DROP TABLE IF EXISTS `Cuidador`");
        _db.execSQL("DROP TABLE IF EXISTS `Usuario`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsContato = new HashMap<String, TableInfo.Column>(4);
        _columnsContato.put("contato_id", new TableInfo.Column("contato_id", "INTEGER", true, 1));
        _columnsContato.put("contato_nome", new TableInfo.Column("contato_nome", "TEXT", false, 0));
        _columnsContato.put("contato_relacionamento", new TableInfo.Column("contato_relacionamento", "TEXT", false, 0));
        _columnsContato.put("contato_foto", new TableInfo.Column("contato_foto", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContato = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContato = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContato = new TableInfo("Contato", _columnsContato, _foreignKeysContato, _indicesContato);
        final TableInfo _existingContato = TableInfo.read(_db, "Contato");
        if (! _infoContato.equals(_existingContato)) {
          throw new IllegalStateException("Migration didn't properly handle Contato(com.example.pm.assistant.data.Contato).\n"
                  + " Expected:\n" + _infoContato + "\n"
                  + " Found:\n" + _existingContato);
        }
        final HashMap<String, TableInfo.Column> _columnsCuidador = new HashMap<String, TableInfo.Column>(6);
        _columnsCuidador.put("cuidador_id", new TableInfo.Column("cuidador_id", "INTEGER", true, 1));
        _columnsCuidador.put("cuidador_email", new TableInfo.Column("cuidador_email", "TEXT", false, 0));
        _columnsCuidador.put("cuidador_senha", new TableInfo.Column("cuidador_senha", "TEXT", false, 0));
        _columnsCuidador.put("cuidador_celular", new TableInfo.Column("cuidador_celular", "TEXT", false, 0));
        _columnsCuidador.put("cuidador_endereco", new TableInfo.Column("cuidador_endereco", "TEXT", false, 0));
        _columnsCuidador.put("contato_id", new TableInfo.Column("contato_id", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCuidador = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCuidador = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCuidador = new TableInfo("Cuidador", _columnsCuidador, _foreignKeysCuidador, _indicesCuidador);
        final TableInfo _existingCuidador = TableInfo.read(_db, "Cuidador");
        if (! _infoCuidador.equals(_existingCuidador)) {
          throw new IllegalStateException("Migration didn't properly handle Cuidador(com.example.pm.assistant.data.Cuidador).\n"
                  + " Expected:\n" + _infoCuidador + "\n"
                  + " Found:\n" + _existingCuidador);
        }
        final HashMap<String, TableInfo.Column> _columnsUsuario = new HashMap<String, TableInfo.Column>(5);
        _columnsUsuario.put("usuario_id", new TableInfo.Column("usuario_id", "INTEGER", true, 1));
        _columnsUsuario.put("usuario_nome", new TableInfo.Column("usuario_nome", "TEXT", false, 0));
        _columnsUsuario.put("usuario_sexo", new TableInfo.Column("usuario_sexo", "INTEGER", true, 0));
        _columnsUsuario.put("usuario_dataDeNascimento", new TableInfo.Column("usuario_dataDeNascimento", "TEXT", false, 0));
        _columnsUsuario.put("dicaAtiv", new TableInfo.Column("dicaAtiv", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsuario = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsuario = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsuario = new TableInfo("Usuario", _columnsUsuario, _foreignKeysUsuario, _indicesUsuario);
        final TableInfo _existingUsuario = TableInfo.read(_db, "Usuario");
        if (! _infoUsuario.equals(_existingUsuario)) {
          throw new IllegalStateException("Migration didn't properly handle Usuario(com.example.pm.assistant.data.Usuario).\n"
                  + " Expected:\n" + _infoUsuario + "\n"
                  + " Found:\n" + _existingUsuario);
        }
      }
    }, "04ee1ae24fd32329b0d3391b13b1bd68", "1cdd48656983db7f3453270ec2c400d5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Contato","Cuidador","Usuario");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Contato`");
      _db.execSQL("DELETE FROM `Cuidador`");
      _db.execSQL("DELETE FROM `Usuario`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public Dao dao() {
    if (_dao != null) {
      return _dao;
    } else {
      synchronized(this) {
        if(_dao == null) {
          _dao = new Dao_Impl(this);
        }
        return _dao;
      }
    }
  }
}
