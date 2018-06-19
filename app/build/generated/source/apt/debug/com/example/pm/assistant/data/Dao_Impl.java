package com.example.pm.assistant.data;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Dao_Impl implements Dao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfContato;

  private final EntityInsertionAdapter __insertionAdapterOfCuidador;

  private final EntityInsertionAdapter __insertionAdapterOfUsuario;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfContato;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCuidador;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUsuario;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfContato;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCuidador;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUsuario;

  public Dao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfContato = new EntityInsertionAdapter<Contato>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Contato`(`contato_id`,`contato_nome`,`contato_relacionamento`,`contato_foto`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contato value) {
        stmt.bindLong(1, value.getContato_id());
        if (value.getContato_nome() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getContato_nome());
        }
        if (value.getContato_relacionamento() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContato_relacionamento());
        }
        if (value.getContato_foto() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getContato_foto());
        }
      }
    };
    this.__insertionAdapterOfCuidador = new EntityInsertionAdapter<Cuidador>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Cuidador`(`cuidador_id`,`cuidador_email`,`cuidador_senha`,`cuidador_celular`,`cuidador_endereco`,`contato_id`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cuidador value) {
        stmt.bindLong(1, value.getCuidador_id());
        if (value.getCuidador_email() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCuidador_email());
        }
        if (value.getCuidador_senha() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCuidador_senha());
        }
        if (value.getCuidador_celular() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCuidador_celular());
        }
        if (value.getCuidador_endereco() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCuidador_endereco());
        }
        stmt.bindLong(6, value.getContato_id());
      }
    };
    this.__insertionAdapterOfUsuario = new EntityInsertionAdapter<Usuario>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Usuario`(`usuario_id`,`usuario_nome`,`usuario_sexo`,`usuario_dataDeNascimento`,`dicaAtiv`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuario value) {
        stmt.bindLong(1, value.getUsuario_id());
        if (value.getUsuario_nome() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsuario_nome());
        }
        final int _tmp;
        _tmp = value.isUsuario_sexo() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getUsuario_dataDeNascimento() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUsuario_dataDeNascimento());
        }
        final int _tmp_1;
        _tmp_1 = value.isDicaAtiv() ? 1 : 0;
        stmt.bindLong(5, _tmp_1);
      }
    };
    this.__deletionAdapterOfContato = new EntityDeletionOrUpdateAdapter<Contato>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Contato` WHERE `contato_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contato value) {
        stmt.bindLong(1, value.getContato_id());
      }
    };
    this.__deletionAdapterOfCuidador = new EntityDeletionOrUpdateAdapter<Cuidador>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Cuidador` WHERE `cuidador_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cuidador value) {
        stmt.bindLong(1, value.getCuidador_id());
      }
    };
    this.__deletionAdapterOfUsuario = new EntityDeletionOrUpdateAdapter<Usuario>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Usuario` WHERE `usuario_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuario value) {
        stmt.bindLong(1, value.getUsuario_id());
      }
    };
    this.__updateAdapterOfContato = new EntityDeletionOrUpdateAdapter<Contato>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Contato` SET `contato_id` = ?,`contato_nome` = ?,`contato_relacionamento` = ?,`contato_foto` = ? WHERE `contato_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contato value) {
        stmt.bindLong(1, value.getContato_id());
        if (value.getContato_nome() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getContato_nome());
        }
        if (value.getContato_relacionamento() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContato_relacionamento());
        }
        if (value.getContato_foto() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getContato_foto());
        }
        stmt.bindLong(5, value.getContato_id());
      }
    };
    this.__updateAdapterOfCuidador = new EntityDeletionOrUpdateAdapter<Cuidador>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Cuidador` SET `cuidador_id` = ?,`cuidador_email` = ?,`cuidador_senha` = ?,`cuidador_celular` = ?,`cuidador_endereco` = ?,`contato_id` = ? WHERE `cuidador_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cuidador value) {
        stmt.bindLong(1, value.getCuidador_id());
        if (value.getCuidador_email() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCuidador_email());
        }
        if (value.getCuidador_senha() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCuidador_senha());
        }
        if (value.getCuidador_celular() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCuidador_celular());
        }
        if (value.getCuidador_endereco() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCuidador_endereco());
        }
        stmt.bindLong(6, value.getContato_id());
        stmt.bindLong(7, value.getCuidador_id());
      }
    };
    this.__updateAdapterOfUsuario = new EntityDeletionOrUpdateAdapter<Usuario>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Usuario` SET `usuario_id` = ?,`usuario_nome` = ?,`usuario_sexo` = ?,`usuario_dataDeNascimento` = ?,`dicaAtiv` = ? WHERE `usuario_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Usuario value) {
        stmt.bindLong(1, value.getUsuario_id());
        if (value.getUsuario_nome() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsuario_nome());
        }
        final int _tmp;
        _tmp = value.isUsuario_sexo() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getUsuario_dataDeNascimento() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUsuario_dataDeNascimento());
        }
        final int _tmp_1;
        _tmp_1 = value.isDicaAtiv() ? 1 : 0;
        stmt.bindLong(5, _tmp_1);
        stmt.bindLong(6, value.getUsuario_id());
      }
    };
  }

  @Override
  public void addContato(Contato contato) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfContato.insert(contato);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addCuidador(Cuidador cuidador) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCuidador.insert(cuidador);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addUsuario(Usuario usuario) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUsuario.insert(usuario);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteContato(Contato... contato) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfContato.handleMultiple(contato);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCuidador(Cuidador... cuidador) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCuidador.handleMultiple(cuidador);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUsuario(Usuario... usuario) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUsuario.handleMultiple(usuario);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateContato(Contato contato) {
    __db.beginTransaction();
    try {
      __updateAdapterOfContato.handle(contato);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updadeCuidador(Cuidador cuidador) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCuidador.handle(cuidador);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUsuario(Usuario usuario) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUsuario.handle(usuario);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Contato> getAllContatos() {
    final String _sql = "SELECT * from Contato";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfContatoId = _cursor.getColumnIndexOrThrow("contato_id");
      final int _cursorIndexOfContatoNome = _cursor.getColumnIndexOrThrow("contato_nome");
      final int _cursorIndexOfContatoRelacionamento = _cursor.getColumnIndexOrThrow("contato_relacionamento");
      final int _cursorIndexOfContatoFoto = _cursor.getColumnIndexOrThrow("contato_foto");
      final List<Contato> _result = new ArrayList<Contato>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Contato _item;
        final String _tmpContato_nome;
        _tmpContato_nome = _cursor.getString(_cursorIndexOfContatoNome);
        final String _tmpContato_relacionamento;
        _tmpContato_relacionamento = _cursor.getString(_cursorIndexOfContatoRelacionamento);
        final String _tmpContato_foto;
        _tmpContato_foto = _cursor.getString(_cursorIndexOfContatoFoto);
        _item = new Contato(_tmpContato_nome,_tmpContato_relacionamento,_tmpContato_foto);
        final int _tmpContato_id;
        _tmpContato_id = _cursor.getInt(_cursorIndexOfContatoId);
        _item.setContato_id(_tmpContato_id);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Cuidador> getAllCuidadores() {
    final String _sql = "SELECT * from Cuidador";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCuidadorId = _cursor.getColumnIndexOrThrow("cuidador_id");
      final int _cursorIndexOfCuidadorEmail = _cursor.getColumnIndexOrThrow("cuidador_email");
      final int _cursorIndexOfCuidadorSenha = _cursor.getColumnIndexOrThrow("cuidador_senha");
      final int _cursorIndexOfCuidadorCelular = _cursor.getColumnIndexOrThrow("cuidador_celular");
      final int _cursorIndexOfCuidadorEndereco = _cursor.getColumnIndexOrThrow("cuidador_endereco");
      final int _cursorIndexOfContatoId = _cursor.getColumnIndexOrThrow("contato_id");
      final List<Cuidador> _result = new ArrayList<Cuidador>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Cuidador _item;
        final String _tmpCuidador_email;
        _tmpCuidador_email = _cursor.getString(_cursorIndexOfCuidadorEmail);
        final String _tmpCuidador_senha;
        _tmpCuidador_senha = _cursor.getString(_cursorIndexOfCuidadorSenha);
        final String _tmpCuidador_celular;
        _tmpCuidador_celular = _cursor.getString(_cursorIndexOfCuidadorCelular);
        final String _tmpCuidador_endereco;
        _tmpCuidador_endereco = _cursor.getString(_cursorIndexOfCuidadorEndereco);
        final int _tmpContato_id;
        _tmpContato_id = _cursor.getInt(_cursorIndexOfContatoId);
        _item = new Cuidador(_tmpCuidador_email,_tmpCuidador_senha,_tmpCuidador_celular,_tmpCuidador_endereco,_tmpContato_id);
        final int _tmpCuidador_id;
        _tmpCuidador_id = _cursor.getInt(_cursorIndexOfCuidadorId);
        _item.setCuidador_id(_tmpCuidador_id);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Usuario getUsuario() {
    final String _sql = "SELECT * from Usuario";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUsuarioId = _cursor.getColumnIndexOrThrow("usuario_id");
      final int _cursorIndexOfUsuarioNome = _cursor.getColumnIndexOrThrow("usuario_nome");
      final int _cursorIndexOfUsuarioSexo = _cursor.getColumnIndexOrThrow("usuario_sexo");
      final int _cursorIndexOfUsuarioDataDeNascimento = _cursor.getColumnIndexOrThrow("usuario_dataDeNascimento");
      final int _cursorIndexOfDicaAtiv = _cursor.getColumnIndexOrThrow("dicaAtiv");
      final Usuario _result;
      if(_cursor.moveToFirst()) {
        final String _tmpUsuario_nome;
        _tmpUsuario_nome = _cursor.getString(_cursorIndexOfUsuarioNome);
        final boolean _tmpUsuario_sexo;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfUsuarioSexo);
        _tmpUsuario_sexo = _tmp != 0;
        final String _tmpUsuario_dataDeNascimento;
        _tmpUsuario_dataDeNascimento = _cursor.getString(_cursorIndexOfUsuarioDataDeNascimento);
        final boolean _tmpDicaAtiv;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfDicaAtiv);
        _tmpDicaAtiv = _tmp_1 != 0;
        _result = new Usuario(_tmpUsuario_nome,_tmpUsuario_sexo,_tmpUsuario_dataDeNascimento,_tmpDicaAtiv);
        final int _tmpUsuario_id;
        _tmpUsuario_id = _cursor.getInt(_cursorIndexOfUsuarioId);
        _result.setUsuario_id(_tmpUsuario_id);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Contato getContato(int id) {
    final String _sql = "SELECT * from Contato where contato_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfContatoId = _cursor.getColumnIndexOrThrow("contato_id");
      final int _cursorIndexOfContatoNome = _cursor.getColumnIndexOrThrow("contato_nome");
      final int _cursorIndexOfContatoRelacionamento = _cursor.getColumnIndexOrThrow("contato_relacionamento");
      final int _cursorIndexOfContatoFoto = _cursor.getColumnIndexOrThrow("contato_foto");
      final Contato _result;
      if(_cursor.moveToFirst()) {
        final String _tmpContato_nome;
        _tmpContato_nome = _cursor.getString(_cursorIndexOfContatoNome);
        final String _tmpContato_relacionamento;
        _tmpContato_relacionamento = _cursor.getString(_cursorIndexOfContatoRelacionamento);
        final String _tmpContato_foto;
        _tmpContato_foto = _cursor.getString(_cursorIndexOfContatoFoto);
        _result = new Contato(_tmpContato_nome,_tmpContato_relacionamento,_tmpContato_foto);
        final int _tmpContato_id;
        _tmpContato_id = _cursor.getInt(_cursorIndexOfContatoId);
        _result.setContato_id(_tmpContato_id);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Cuidador getCuidador(int id) {
    final String _sql = "SELECT * from Cuidador where cuidador_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfCuidadorId = _cursor.getColumnIndexOrThrow("cuidador_id");
      final int _cursorIndexOfCuidadorEmail = _cursor.getColumnIndexOrThrow("cuidador_email");
      final int _cursorIndexOfCuidadorSenha = _cursor.getColumnIndexOrThrow("cuidador_senha");
      final int _cursorIndexOfCuidadorCelular = _cursor.getColumnIndexOrThrow("cuidador_celular");
      final int _cursorIndexOfCuidadorEndereco = _cursor.getColumnIndexOrThrow("cuidador_endereco");
      final int _cursorIndexOfContatoId = _cursor.getColumnIndexOrThrow("contato_id");
      final Cuidador _result;
      if(_cursor.moveToFirst()) {
        final String _tmpCuidador_email;
        _tmpCuidador_email = _cursor.getString(_cursorIndexOfCuidadorEmail);
        final String _tmpCuidador_senha;
        _tmpCuidador_senha = _cursor.getString(_cursorIndexOfCuidadorSenha);
        final String _tmpCuidador_celular;
        _tmpCuidador_celular = _cursor.getString(_cursorIndexOfCuidadorCelular);
        final String _tmpCuidador_endereco;
        _tmpCuidador_endereco = _cursor.getString(_cursorIndexOfCuidadorEndereco);
        final int _tmpContato_id;
        _tmpContato_id = _cursor.getInt(_cursorIndexOfContatoId);
        _result = new Cuidador(_tmpCuidador_email,_tmpCuidador_senha,_tmpCuidador_celular,_tmpCuidador_endereco,_tmpContato_id);
        final int _tmpCuidador_id;
        _tmpCuidador_id = _cursor.getInt(_cursorIndexOfCuidadorId);
        _result.setCuidador_id(_tmpCuidador_id);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
