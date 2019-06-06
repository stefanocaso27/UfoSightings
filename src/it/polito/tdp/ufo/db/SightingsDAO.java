package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Avvistamenti;
import it.polito.tdp.ufo.model.Sighting;
import it.polito.tdp.ufo.model.Stato;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Sighting(res.getInt("id"),
							res.getTimestamp("datetime").toLocalDateTime(),
							res.getString("city"), 
							res.getString("state"), 
							res.getString("country"),
							res.getString("shape"),
							res.getInt("duration"),
							res.getString("duration_hm"),
							res.getString("comments"),
							res.getDate("date_posted").toLocalDate(),
							res.getDouble("latitude"), 
							res.getDouble("longitude"))) ;
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Avvistamenti> listTendina() {
		String sql = "SELECT YEAR(DATETIME) AS anno, COUNT(YEAR(DATETIME)) AS numero " + 
				"FROM sighting " + 
				"WHERE country = 'us' " + 
				"GROUP BY YEAR(DATETIME)";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Avvistamenti> list = new ArrayList<Avvistamenti>();
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Avvistamenti a = new Avvistamenti(res.getInt("anno"), res.getInt("numero"));
				list.add(a);
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Stato> getStati(int anno) {
		String sql = "SELECT state as stato, COUNT(state) AS numero " + 
				"FROM sighting " + 
				"WHERE YEAR(DATETIME) = ? AND country = 'us' " + 
				"GROUP BY state" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			List<Stato> list = new ArrayList<Stato>();
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Stato s = new Stato(res.getString("stato"), res.getInt("numero"));
				list.add(s);
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}	
	}

	public boolean esisteArco(Stato s1, Stato s2, int anno) {
		String sql = "select count(*) as cnt " + 
				"from Sighting s1,Sighting s2 " + 
				"where Year(s1.datetime) = Year(s2.datetime) " + 
				"and Year(s1.datetime) = ? and " + 
				"s1.state = ? and s2.state = ? and " + 
				"s1.country = 'us' and s2.country = 'us' " + 
				"and s2.datetime > s1.datetime";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setString(2, s1.getId());
			st.setString(3, s2.getId());
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				if(res.getInt("cnt") > 0) {
					conn.close();

					return true;
				}
				else {
					conn.close();

					return false;
				}
			} else
				return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
}
