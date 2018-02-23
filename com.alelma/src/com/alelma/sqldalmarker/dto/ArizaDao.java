package src.com.alelma.sqldalmarker.dto;

import com.sqldalmaker.DataStore;

import java.util.List;
import src.com.alelma.sqldalmarker.dao.Ariza;

/**
 * This class is created by SQL DAL Maker. Don't modify it manually.
 * SQL DAL Maker project web-site: http://sqldalmaker.sourceforge.net
 */
public class ArizaDao {

	protected final DataStore ds;

	public ArizaDao(DataStore ds) {
		this.ds = ds;
	}

	/**
	 * CRUD-Create. The table 'ariza'.
	 * Auto-generated values are assigned to appropriate fields of DTO.
	 * Returns the number of affected rows or -1 on error.
	 */
	public int createAriza(Ariza p) throws Exception {

		String sql = "INSERT INTO ariza (tanim, teslimtarih, uzuntanim, kayittarih) " 
				 + "VALUES (?, ?, ?, ?)";

		String []genColNames = new String[] {"id"};
		Object []genValues = new Object[genColNames.length];

		int res = ds.insert(sql, genColNames, genValues, p.getTanim(), p.getTeslimtarih(), p.getUzuntanim(), p.getKayittarih());

		p.setId(ds.castGeneratedValue(Integer.class, genValues[0]));

		return res;
	}

	/**
	 * CRUD-Read. The table 'ariza'.
	 */
	public List<Ariza> readArizaList() throws Exception {

		String sql = "SELECT * FROM ariza";

		return ds.queryDtoList(sql, new DataStore.RowHandler<Ariza>() {

			public Ariza handleRow(DataStore.RowData rd) throws Exception {
				Ariza obj = new Ariza();
				obj.setId(rd.getValue(Integer.class, "id"));
				obj.setTanim(rd.getValue(String.class, "tanim"));
				obj.setTeslimtarih(rd.getValue(java.util.Date.class, "teslimtarih"));
				obj.setUzuntanim(rd.getValue(String.class, "uzuntanim"));
				obj.setKayittarih(rd.getValue(java.util.Date.class, "kayittarih"));
				return obj;
			}

		});
	}

	/**
	 * CRUD-Read. The table 'ariza'.
	 */
	public Ariza readAriza(Integer id) throws Exception {

		String sql = "SELECT * FROM ariza WHERE id = ?";

		return ds.queryDto(sql, new DataStore.RowHandler<Ariza>() {

			public Ariza handleRow(DataStore.RowData rd) throws Exception {
				Ariza res = new Ariza();
				res.setId(rd.getValue(Integer.class, "id"));
				res.setTanim(rd.getValue(String.class, "tanim"));
				res.setTeslimtarih(rd.getValue(java.util.Date.class, "teslimtarih"));
				res.setUzuntanim(rd.getValue(String.class, "uzuntanim"));
				res.setKayittarih(rd.getValue(java.util.Date.class, "kayittarih"));
				return res;
			}

		}, id);
	}

	/**
	 * CRUD-Update. The table 'ariza'.
	 * Returns the number of affected rows or -1 on error.
	 */
	public int updateAriza(Ariza p) throws Exception {

		String sql = "UPDATE ariza SET " 
				 + " tanim = ?, teslimtarih = ?, uzuntanim = ?, kayittarih = ? " 
				 + "WHERE id = ?";

		return ds.execDML(sql, p.getTanim(), p.getTeslimtarih(), p.getUzuntanim(), p.getKayittarih(), p.getId());
	}

	/**
	 * CRUD-Update. The table 'ariza'.
	 * Returns the number of affected rows or -1 on error.
	 */
	public int updateAriza(String tanim, java.util.Date teslimtarih, String uzuntanim, java.util.Date kayittarih, Integer id) throws Exception {

		String sql = "UPDATE ariza SET " 
				 + " tanim = ?, teslimtarih = ?, uzuntanim = ?, kayittarih = ? " 
				 + "WHERE id = ?";

		return ds.execDML(sql, tanim, teslimtarih, uzuntanim, kayittarih, id);
	}

	/**
	 * CRUD-Delete. The table 'ariza'.
	 * Returns the number of affected rows or -1 on error.
	 */
	public int deleteAriza(Ariza p) throws Exception {

		String sql = "DELETE FROM ariza WHERE id = ?";

		return ds.execDML(sql, p.getId());
	}

	/**
	 * CRUD-Delete. The table 'ariza'.
	 * Returns the number of affected rows or -1 on error.
	 */
	public int deleteAriza(Integer id) throws Exception {

		String sql = "DELETE FROM ariza WHERE id = ?";

		return ds.execDML(sql, id);
	}
}