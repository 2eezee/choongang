package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;

public class JdbcDao implements BDao {
	// JDBC 사용
	private final DataSource dataSource;
	
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// spring에서는 아래 세줄이 공식이다! 라고 생각하고 사용 
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	@Override
	public ArrayList<BDto> boardList()   {
		
		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		System.out.println("BDao boardList start...");
		
		try {
			
			conn = getConnection();
			String query = "SELECT bId, bName, bTitle, bContent, bDate, bHit,"
	                + "            bGroup, bStep, bIndent "
	                + "     FROM   mvc_board order by bGroup desc, bStep asc";

			preparedStatement = conn.prepareStatement(query);
			System.out.println("BDao boardList start...");
			resultSet = preparedStatement.executeQuery();

			// primary key로 조건식 써서 가져오는거 아니면 무조건 while 문을 사용 ㄱㄱ 
			
			while (resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName =  resultSet.getString("bName");
				String bTitle =  resultSet.getString("bTitle");
				String bContent =  resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
				bList.add(dto);
			}
	
			} catch (SQLException e) {
				System.out.println("list dataSource -->" + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if(resultSet != null) resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(conn != null) conn.close();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}     
			
		return bList;
	}

	@Override
	public void write(String bName, String bTitle, String bContent) {
		
		// 1. Insert Into mvc_board
		// 2. prepareStatement 
		// 3. mvc_board_seq
		// 4. bId , bGroup 같게
		// 5.  bStep, bIndent, bDate --> 0, 0 , sysdate
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			String query = "Insert Into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent, bDate ) "
									+ "Values (mvc_board_seq.nextval,?, ?, ?, 0, mvc_board_seq.currval,  0, 0 , sysdate)";
			System.out.println("BDao write  query-->" + query );
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			int rn = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("write  dataSource-->" + e.getMessage() );
			e.printStackTrace();
		} finally {
			try { 
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
					
	}

	@Override
	public BDto contentView(String strId) {
					upHit(strId);
					BDto dto = null;
					Connection connection = null;
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					
					try {
						connection = getConnection();
						String query = "select * from mvc_board where bId = ?";
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.setInt(1, Integer.parseInt(strId));
						resultSet = preparedStatement.executeQuery();

						if(resultSet.next()) {
							int bId = resultSet.getInt("bId");
							String bName = resultSet.getString("bName");
							String bTitle = resultSet.getString("bTitle");
							String bContent = resultSet.getString("bContent");
							Timestamp bDate = resultSet.getTimestamp("bDate");
							int bHit = resultSet.getInt("bHit");
							int bGroup = resultSet.getInt("bGroup");
							int bStep = resultSet.getInt("bStep");
							int bIndent = resultSet.getInt("bIndent");
							dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if(resultSet != null) resultSet.close();
							if(preparedStatement != null) preparedStatement.close();
							if(connection != null) connection.close();
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
					System.out.println("JdbcDao dto.getbName->"+dto.getbName());
					return dto;		
			   }

	// private 으로 만들어지는 이유 : 외부에서 접근 못하고 dao 에서만 ㅇㅋㅇㅋ되도록 
	private void upHit(String strId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, strId);
			
			int rn = preparedStatement.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}		
		
	}

	@Override
	public void modify(String bId, String bName, String bTitle, String bContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ?, where bId = ?";
			System.out.println("Bdao modify query->" + query);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setString(4, bId);
			
			int rn = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@Override
	public BDto reply_view(String strbId) {
		
		BDto dto = null;
		
		 System.out.println("== JdbcDao reply_view start...===");
		 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			String query = "select * from mvc_board where bId = ?";
			System.out.println("Bdao reply_view query->" + query);
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, strbId);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	
		return dto;
	}

	  @Override
      public void reply(String bId, String bName, String bTitle, String bContent, 
                      String bGroup, String bStep,
            String bIndent) {
         // TODO Auto-generated method stub
         // 홍해 기적 
         replyShape(bGroup, bStep);
         
         Connection connection = null;
         PreparedStatement preparedStatement = null;
         
         try {
            connection = getConnection();
            String query = "insert into mvc_board (bId, bName, bTitle, bContent, "
                       + " bGroup, bStep, bIndent)"
                      + " values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, bName);
            preparedStatement.setString(2, bTitle);
            preparedStatement.setString(3, bContent);
            preparedStatement.setInt(4, Integer.parseInt(bGroup));
            preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
            preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
            
            int rn = preparedStatement.executeUpdate();
         } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
         } finally {
            try {
               if(preparedStatement != null) preparedStatement.close();
               if(connection != null) connection.close();
            } catch (Exception e2) {
               // TODO: handle exception
               e2.printStackTrace();
            }
         }
                  
      }

      private void replyShape(String strGroup, String strStep) {
         Connection connection = null;
         PreparedStatement preparedStatement = null;
         
         try {
            connection = getConnection();
            String query = "update mvc_board set bStep = bStep + 1 "
            							+ " where bGroup = ? and bStep > ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(strGroup));
            preparedStatement.setInt(2, Integer.parseInt(strStep));
            
            int rn = preparedStatement.executeUpdate();
            
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            try {
               if(preparedStatement != null) preparedStatement.close();
               if(connection != null) connection.close(); 
            } catch (Exception e2) {
               // TODO: handle exception
               e2.printStackTrace();
            }
         }
     }


	@Override
	public void delete(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			String query = "delete from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int rn = preparedStatement.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		
		}
	}
		
	}


