package myServlet.databaseImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.utils.JdbcUtil;

import mybean.data.FindComment;
import mybean.data.FindInfo;
import mybean.data.PageResult;

public class InfoDatabaseImpl {

	// ��ѯ����
	public PageResult queryShare(Integer currentPage, Integer pageSize, String keyWord) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet resultSet2 = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();

			String whereSql = "";
			if (keyWord != null && keyWord.trim().length() > 0) {
				whereSql = "where info_title like '%" + keyWord + "%' or info_describe like '%" + keyWord + "%'";
			}

			String sql = "select * from (select * from info " + whereSql + " order by info_id desc) AS temp4 limit ?,?";
			// String sql ="select * from info limit ?,?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, (currentPage - 1) * pageSize);
			prepareStatement.setInt(2, pageSize);

			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int infoId = rowSet.getInt(1); // ����ID
				String infoTitle = rowSet.getString(2); // ���ݱ���
				String infoDescribe = rowSet.getString(3); // ���ݼ���
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // ��������
				int type = rowSet.getInt(5); // ���ͣ�0��ʾ�ռǣ�1��ʾȤ��
				int support = rowSet.getInt(6); // ������

				String infoAuthor = rowSet.getString(7); // ����
				String infoPic = rowSet.getString(8); // ����

				FindInfo findInfo = new FindInfo(infoId, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}
			// ��ѯ������
			String sql2 = "select * from info " + whereSql;
			prepareStatement2 = connection.prepareStatement(sql2);
			resultSet2 = prepareStatement2.executeQuery();
			Long totalRecord = 0L;
			while (resultSet2.next()) {
				totalRecord = totalRecord + 1;
			}
			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtil.close(connection, prepareStatement, rowSet);
			JdbcUtil.close(connection, prepareStatement2, resultSet2);
		}
		return pageResult;
	}

	// ��ѯ���20����¼�е�������ǰ��������
	public PageResult queryBanner(Integer currentPage, Integer pageSize) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet resultSet2 = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();

			String sql = "select * from (select * from (select * from (select * from info order by info_id desc) AS temp limit 0,20) AS temp1 order by info_support desc) AS temp2 limit ?,?";
			// String sql ="select * from info limit ?,?";
			prepareStatement = connection.prepareStatement(sql);

			prepareStatement.setInt(1, (currentPage - 1) * pageSize);
			prepareStatement.setInt(2, pageSize);

			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int infoId = rowSet.getInt(1); // ����ID
				String infoTitle = rowSet.getString(2); // ���ݱ���
				String infoDescribe = rowSet.getString(3); // ���ݼ���
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // ��������
				int type = rowSet.getInt(5); // ���ͣ�0��ʾ�ռǣ�1��ʾȤ��
				int support = rowSet.getInt(6); // ������

				String infoAuthor = rowSet.getString(7); // ����
				String infoPic = rowSet.getString(8); // ����

				FindInfo findInfo = new FindInfo(infoId, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}
			// ��ѯ�����û�����
			String sql2 = "select * from (select * from info order by info_id desc) AS temp1 order by info_support desc";
			prepareStatement2 = connection.prepareStatement(sql2);
			resultSet2 = prepareStatement2.executeQuery();
			Long totalRecord = 0L;
			while (resultSet2.next()) {
				totalRecord = totalRecord + 1;
			}
			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtil.close(connection, prepareStatement, rowSet);
			JdbcUtil.close(connection, prepareStatement2, resultSet2);
		}
		return pageResult;
	}

	// ��ѯ��������
	public PageResult queryByHotSupport(Integer currentPage, Integer pageSize) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet resultSet2 = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();

			String sql = "select * from (select * from info order by info_support desc) AS temp5 limit 0,12";

			prepareStatement = connection.prepareStatement(sql);
			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int infoId = rowSet.getInt(1); // ����ID
				String infoTitle = rowSet.getString(2); // ���ݱ���
				String infoDescribe = rowSet.getString(3); // ���ݼ���
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // ��������
				int type = rowSet.getInt(5); // ���ͣ�0��ʾ�ռǣ�1��ʾȤ��
				int support = rowSet.getInt(6); // ������

				String infoAuthor = rowSet.getString(7); // ����
				String infoPic = rowSet.getString(8); // ����

				FindInfo findInfo = new FindInfo(infoId, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}
			// ��ѯ�����û�����
			String sql2 = "select * from (select * from info order by info_support desc) AS temp5 limit 0,12";
			prepareStatement2 = connection.prepareStatement(sql2);
			resultSet2 = prepareStatement2.executeQuery();
			Long totalRecord = 0L;
			while (resultSet2.next()) {
				totalRecord = totalRecord + 1;
			}
			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, prepareStatement, rowSet);
			JdbcUtil.close(connection, prepareStatement2, resultSet2);
		}
		return pageResult;
	}

	// ��ѯ�����ղ�
	public PageResult queryByHotFocus(Integer currentPage, Integer pageSize) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();

			// String sql ="select * from info left join (select * from (SELECT info_id,
			// count(*) AS count FROM focus GROUP BY info_id ORDER BY count DESC) AS count2
			// limit 0,6) AS count3 on info.info_id=count3.info_id";
			String sql = "select * from info right join (select * from (SELECT info_id, count(*) AS count FROM focus GROUP BY info_id ORDER BY count DESC) AS count2 limit 0,10) AS count3 on info.info_id=count3.info_id";

			prepareStatement = connection.prepareStatement(sql);
			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int infoId = rowSet.getInt(1); // ����ID
				String infoTitle = rowSet.getString(2); // ���ݱ���
				String infoDescribe = rowSet.getString(3); // ���ݼ���
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // ��������
				int type = rowSet.getInt(5); // ���ͣ�0��ʾ�ռǣ�1��ʾȤ��
				int support = rowSet.getInt(6); // ������

				String infoAuthor = rowSet.getString(7); // ����
				String infoPic = rowSet.getString(8); // ����

				FindInfo findInfo = new FindInfo(infoId, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}

			Long totalRecord = 10L;

			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, prepareStatement, rowSet);
		}
		return pageResult;
	}

	// ��ѯ�ҵ�����ղ�
	public PageResult queryByMyFocus(Integer currentPage, Integer pageSize, String logName) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();

			// String sql ="select * from info left join (select * from (SELECT info_id,
			// count(*) AS count FROM focus GROUP BY info_id ORDER BY count DESC) AS count2
			// limit 0,6) AS count3 on info.info_id=count3.info_id";
			String sql = "select * from (select info.* from info left join focus on info.info_id=focus.info_id where focus.username=? order by focus.info_id desc) AS temp10 limit ?,?";

			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, logName);
			prepareStatement.setInt(2, (currentPage - 1) * pageSize);
			prepareStatement.setInt(3, pageSize);
			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int infoId = rowSet.getInt(1); // ����ID
				String infoTitle = rowSet.getString(2); // ���ݱ���
				String infoDescribe = rowSet.getString(3); // ���ݼ���
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // ��������
				int type = rowSet.getInt(5); // ���ͣ�0��ʾ�ռǣ�1��ʾȤ��
				int support = rowSet.getInt(6); // ������

				String infoAuthor = rowSet.getString(7); // ����
				String infoPic = rowSet.getString(8); // ����

				FindInfo findInfo = new FindInfo(infoId, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}

			Long totalRecord = 12L;

			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, prepareStatement, rowSet);
		}
		return pageResult;
	}

	public PageResult queryByInfoId(Integer currentPage, Integer pageSize, String infoId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();
			String sql = "select * from info where info_id=" + infoId;

			prepareStatement = connection.prepareStatement(sql);
			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int id = rowSet.getInt(1); // ����ID
				String infoTitle = rowSet.getString(2); // ���ݱ���
				String infoDescribe = rowSet.getString(3); // ���ݼ���
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // ��������
				int type = rowSet.getInt(5); // ���ͣ�0��ʾ�ռǣ�1��ʾȤ��
				int support = rowSet.getInt(6); // ������

				String infoAuthor = rowSet.getString(7); // ����
				String infoPic = rowSet.getString(8); // ����

				FindInfo findInfo = new FindInfo(id, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}

			Long totalRecord = 10L;

			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, prepareStatement, rowSet);
		}
		return pageResult;
	}

	public PageResult queryCommentByInfoId(Integer currentPage, Integer pageSize, String infoId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PageResult pageResult = null;
		try {
			connection = JdbcUtil.getConnection();
			String sql = "select * from comment where comment_info='" + infoId + "' order by comment_id desc";

			prepareStatement = connection.prepareStatement(sql);
			rowSet = prepareStatement.executeQuery();

			List<FindComment> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int id = rowSet.getInt(1); // ����ID
				String commentUser = rowSet.getString(2); // ���ݱ���
				int commentInfo = rowSet.getInt(3); // ���ݼ���
				String commentDetail = rowSet.getString(4); // ��������

				FindComment findComment = new FindComment(id, commentUser, commentInfo, commentDetail);
				findInfoList.add(findComment);
			}

			Long totalRecord = 10L;

			pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(connection, prepareStatement, rowSet);
		}
		return pageResult;
	}

}
