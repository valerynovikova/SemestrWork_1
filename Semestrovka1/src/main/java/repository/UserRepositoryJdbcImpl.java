package repository;

import dto.UserDTO;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

	//language=SQL
	private final static String SQL_SAVE_USER = "insert into users (nickname, email, login, password, avatar_url) values (?, ?, ?, ?, ?)";

	//language=SQL
	private final static String SQL_FIND_USER_BY_LOGIN = "select * from users where login = ?";

	//language=SQL
	private static final String SQL_UPDATE_USER_AVATAR = "UPDATE users SET avatar_url = ? WHERE login = ?";

	//language=SQL
	private static final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";

	//language=SQL
	private static final String SQL_FIND_BY_NICK = "SELECT * FROM users WHERE nickname = ?";

	//language=SQL
	private static final String SQL_FIND_ALL = "SELECT * FROM users";

	private final DataSource dataSource;

	private final Function<ResultSet, UserDTO> rowMapper = row -> {
		try {
			int id = row.getInt("id");
			String nick = row.getString("nickname");
			String email = row.getString("email");
			String login = row.getString("login");
			String password = row.getString("password");
			String avatarUrl = row.getString("avatar_url");
			return new UserDTO(id, nick, email, login, password, avatarUrl);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	};

	@Override
	public void save(UserDTO user) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
			preparedStatement.setString(1, user.getNick());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getAvatarUrl());
			preparedStatement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public Optional<UserDTO> findUserByLogin(String login) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
			preparedStatement.setString(1,login);
			ResultSet row = preparedStatement.executeQuery();
			if(row.next()){
				UserDTO user = rowMapper.apply(row);
				return Optional.of(user);
			} else {
				return Optional.empty();
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public void updateAvatar(UserDTO userDTO) {
		try (Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_AVATAR)) {
			preparedStatement.setString(1, userDTO.getAvatarUrl());
			preparedStatement.setString(2, userDTO.getLogin());
			preparedStatement.executeUpdate();
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public UserDTO findById(int id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
			preparedStatement.setInt(1, id);
			ResultSet row = preparedStatement.executeQuery();
			if (row.next()){
				return rowMapper.apply(row);
			} else {
				return null;
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public UserDTO findByNick(String nick) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NICK)) {
			preparedStatement.setString(1, nick);
			ResultSet row = preparedStatement.executeQuery();
			if (row.next()){
				return rowMapper.apply(row);
			} else {
				return null;
			}
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}

	@Override
	public List<UserDTO> findAll() {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL)) {
			ResultSet row = preparedStatement.executeQuery();
			List<UserDTO> list = new ArrayList<>();
			while (row.next()){
				list.add(rowMapper.apply(row));
			}
			return list;
		} catch (SQLException throwables) {
			throw new IllegalArgumentException(throwables);
		}
	}
}
