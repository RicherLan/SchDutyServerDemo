package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import object.DTComCh;
import object.DTComRoot;
import object.Dongtai;
import object.DongtaiMsg;
import object.DongtaiPCTNum;
import util.User;

/*
 * 动态的数据库操作
 */
public class DongtaiDBService {

	// 获得某条动态的主人
	public static String getPhonenumberOfDongtai(int dongtaiid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from dongtai where id='" + dongtaiid + "'";
			ResultSet eResultSet = conn.createStatement().executeQuery(sql);
			if (eResultSet.next()) {
				return eResultSet.getString(1);
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 动态添加
	public static int addDongtai(String type, String userid, String text, int picturenum, long time) {
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO dongtai(type,userid,text, picturenum,time) VALUES(?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, type);
			pst.setString(2, userid);
			pst.setString(3, text);
			pst.setInt(4, picturenum);
			pst.setLong(5, time);
			int row = pst.executeUpdate();
			if (row <= 0) {
				return -1;
			}
			int id = -1;
			ResultSet resultSet = pst.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 发表动态时 上传上图片
	public static String addImageToDongtai(int dongtaid, int fileid, long time) {

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql2 = "select picture from dongtai where id='" + dongtaid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql2);
			String string = "";
			if (resultSet.next()) {
				string = resultSet.getString(1);
				if (string == null) {
					string = "";
				}
			}
			string = (string + " " + fileid).trim();
			String sql = "update dongtai set picture='" + string + "',time='" + time + "' where id='" + dongtaid + "'";

			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
				return "error";
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 用户给某一条动态点赞
	public static int dongtaipraise(int dongtaiid, String userid, long time) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from dongtai where id='" + dongtaiid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				String reciverid = resultSet.getString(1);

				sql = "insert into dongtaimsg(dongtaiid,userid,type,time,reciverid) values(?,?,?,?,?)";
				PreparedStatement pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pStatement.setInt(1, dongtaiid);
				pStatement.setString(2, userid);
				pStatement.setString(3, "praise");
				pStatement.setLong(4, time);
				pStatement.setString(5, reciverid);
				int row = pStatement.executeUpdate();

				if (row < 0) {
					return -1;
				}

				ResultSet rs = pStatement.getGeneratedKeys();
				int id = -1;
				if (rs.next()) {
					id = rs.getInt(1);

					sql = "update dongtai set praisenum=praisenum+1 where id='" + dongtaiid + "'";
					int row2 = conn.createStatement().executeUpdate(sql);
					if (row2 > 0) {
						return id;

					} else {
						// 撤销
						sql = "delete from dongtaipraise where id='" + id + "'";
						conn.createStatement().executeUpdate(sql);
						return -1;
					}

				}

				return id;
			}

			return -1;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户给动态评论
	public static int dongtaiComment(int dongtaiid, String userid, long time, String message) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String s1l2 = "select userid from dongtai where id='" + dongtaiid + "'";

			ResultSet resultSet = conn.createStatement().executeQuery(s1l2);
			if (resultSet.next()) {
				String ph = resultSet.getString(1);
				String sql = "insert into dongtaimsg(dongtaiid,userid,type,time,msg,reciverid) values(?,?,?,?,?,?)";
				PreparedStatement pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pStatement.setInt(1, dongtaiid);
				pStatement.setString(2, userid);
				pStatement.setString(3, "todongtai");
				pStatement.setLong(4, time);
				pStatement.setString(5, message);
				pStatement.setString(6, ph);

				int row = pStatement.executeUpdate();

				if (row < 0) {
					return -1;
				}

				ResultSet rs = pStatement.getGeneratedKeys();
				int id = -1;
				if (rs.next()) {
					id = rs.getInt(1);

					sql = "update dongtaimsg set rootcommentid=" + id + " where id='" + id + "'";
					conn.createStatement().executeUpdate(sql);

					sql = "update dongtai set commentnum=commentnum+1 where id='" + dongtaiid + "'";
					int row2 = conn.createStatement().executeUpdate(sql);
					if (row2 > 0) {
						return id;

					} else {
						// 撤销
						sql = "delete from dongtaimsg where id='" + id + "'";
						conn.createStatement().executeUpdate(sql);
						return -1;
					}

				}

				return id;
			}

			return -1;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户给某动态的评论 评论
	public static int dongtaicommentComment(int dongtaiid, String userid, int commentid, long time, String msg) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "insert into dongtaicomment(dongtaiid,userid,type,commentid,time,msg) values(?,?,?,?,?,?)";
			PreparedStatement pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStatement.setInt(1, dongtaiid);
			pStatement.setString(2, userid);
			pStatement.setString(3, "tocomment");
			pStatement.setInt(4, commentid);
			pStatement.setLong(5, time);
			pStatement.setString(6, msg);

			int row = pStatement.executeUpdate();

			if (row <= 0) {
				return -1;
			}

			ResultSet rs = pStatement.getGeneratedKeys();
			int id = -1;
			if (rs.next()) {
				id = rs.getInt(1);

				sql = "update dongtai set commentnum=commentnum+1 where id='" + dongtaiid + "'";
				int row2 = conn.createStatement().executeUpdate(sql);
				if (row2 > 0) {
					return id;

				} else {
					// 撤销
					sql = "delete from dongtaicomment where id='" + id + "'";
					conn.createStatement().executeUpdate(sql);
					return -1;
				}

			}

			return id;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 修改 点赞表 消息的读取状态 所有小于该id 的评论
	public static String changeDongtaiPraiseMsgState(int id, String state) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String s1l2 = "select dongtaiid from dongtaipraise where id='" + id + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(s1l2);
			if (resultSet.next()) {
				int dongtaiid = resultSet.getInt(1);
				String sql = "update dongtaipraise set state='" + state + "' where dongtaiid='" + dongtaiid
						+ "' and id<='" + id + "'";

				int row = conn.createStatement().executeUpdate(sql);

				if (row <= 0) {
					return "errordatabase";
				}

				return "ok";
			}

			return "errordatabase";
		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 修改 动态 消息的读取状态 所有小于该id 的评论
	public static String changeDongtaiMsgState(String ph, int id, String state) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			long time = System.currentTimeMillis();
			String sql = "update dongtaimsg set state='" + state + "' where reciverid='" + ph + "' and id<='" + id
					+ "'";

			int row = conn.createStatement().executeUpdate(sql);

			if (row <= 0) {
				return "errordatabase";
			}

			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	// 拿到自己的动态未读消息 一般是刚上线的时候
	public static Vector<DongtaiMsg> getNewDongtaiMsgByPhonenumber(String phonenumber) {

		Connection conn = null;
		Vector<DongtaiMsg> dongtaiMsgs = new Vector<DongtaiMsg>();
		try {
			conn = DBManager.getConnection();
			String sql = "select * from dongtaipraise where state='未读' and dongtaiid in (select id from dongtai where userid='"
					+ phonenumber + "')";

			ResultSet rSet = conn.createStatement().executeQuery(sql);
			while (rSet.next()) {

				int id = rSet.getInt(1);
				int dongtaiid = rSet.getInt(2);
				String userid = rSet.getString(3);
				long time = rSet.getLong(4);
				User user = UserDBService.getUserInfoByPhonenumber(userid);
				if (user != null) {
					DongtaiMsg dongtaiMsg = new DongtaiMsg();
					dongtaiMsg.msgid = (id);
					dongtaiMsg.type = ("praise");
					dongtaiMsg.dongtaiid = (dongtaiid);
					dongtaiMsg.userid = (user.getPhonenumber());
					dongtaiMsg.username = (user.getNickname());
					dongtaiMsg.usericon = (user.getIcon());
					dongtaiMsg.sex = (user.getSex());
					dongtaiMsg.schoolname = (user.getSchoolname());
					// dongtaiMsg.setDepartmentname(user.getDepartmentname());
					dongtaiMsg.time = (time);
					dongtaiMsgs.add(dongtaiMsg);
				}

			}

			sql = "select * from dongtaicomment where state='未读' and reciverid='" + phonenumber + "'";
			rSet = conn.createStatement().executeQuery(sql);
			while (rSet.next()) {

				int id = rSet.getInt(1);
				int dongtaiid = rSet.getInt(2);
				String userid = rSet.getString(3);
				String type = rSet.getString(4);
				int commentid = rSet.getInt(5);
				long time = rSet.getLong(6);
				String msg = rSet.getString(8);
				DongtaiMsg dongtaiMsg = new DongtaiMsg();

				if (type.equals("tocomment")) {
					sql = "select userid from dongtaicomment where id='" + commentid + "'";
					ResultSet resultSet = conn.createStatement().executeQuery(sql);
					if (resultSet.next()) {
						String becommenteduserid = resultSet.getString(1);
						sql = "select nickname from users where phonenumber='" + becommenteduserid + "')";
						ResultSet eSet = conn.createStatement().executeQuery(sql);
						if (eSet.next()) {
							String becommentedusername = eSet.getString(1);
							User user1 = UserDBService.getUserInfoByPhonenumber(userid);
							if (user1 != null && becommentedusername != null) {

								dongtaiMsg.msgid = (id);
								dongtaiMsg.type = (type);
								dongtaiMsg.dongtaiid = (dongtaiid);
								dongtaiMsg.userid = (user1.getPhonenumber());
								dongtaiMsg.username = (user1.getNickname());
								dongtaiMsg.usericon = (user1.getIcon());
								dongtaiMsg.sex = (user1.getSex());
								dongtaiMsg.schoolname = (user1.getSchoolname());
								// dongtaiMsg.setDepartmentname(user1.getDepartmentname());
								dongtaiMsg.time = (time);
								dongtaiMsg.commentid = (commentid);
								dongtaiMsg.becommenteduserid = (becommenteduserid);
								dongtaiMsg.becommentedusername = (becommentedusername);
								dongtaiMsg.msg = (msg);

								dongtaiMsgs.add(dongtaiMsg);
							}
						}
					}

				}

			}

			return dongtaiMsgs;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtaiMsgs;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	// 获得动态某一条评论的主人
	public static String getphonenumberOfDongtaiComment(int commentid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			long time = System.currentTimeMillis();
			String sql = "select userid from dongtaicomment where id = '" + commentid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得某动态的点赞 评论 转发数
	public static DongtaiPCTNum getDongtaiPNCNumByDTId(int dongtaiid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select praisenum,commentnum,transmitnum from dongtai where id='" + dongtaiid + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				int praisenum = resultSet.getInt(1);
				int commentnum = resultSet.getInt(2);
				int transmitnum = resultSet.getInt(3);
				DongtaiPCTNum dongtaiPCTNum = new DongtaiPCTNum();
				dongtaiPCTNum.id = dongtaiid;
				dongtaiPCTNum.pNum = praisenum;
				dongtaiPCTNum.cNum = commentnum;
				dongtaiPCTNum.tNum = transmitnum;
				return dongtaiPCTNum;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得多条动态的点赞 评论 转发数
	public static Vector<DongtaiPCTNum> getDongtaiPNCNumByDTId(Vector<Integer> dongtaiids) {
		Connection conn = null;
		Vector<DongtaiPCTNum> dongtaiPCTNums = new Vector<DongtaiPCTNum>();
		try {
			conn = DBManager.getConnection();
			String sql = "select praisenum,commentnum,transmitnum from dongtai where id=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			for (int i = 0; i < dongtaiids.size(); ++i) {
				int dngtaiid = dongtaiids.get(i);
				pStatement.setInt(1, dngtaiid);
				ResultSet resultSet = pStatement.executeQuery();
				if (resultSet.next()) {
					int praisenum = resultSet.getInt(1);
					int commentnum = resultSet.getInt(2);
					int transmitnum = resultSet.getInt(3);
					DongtaiPCTNum dongtaiPCTNum = new DongtaiPCTNum();
					dongtaiPCTNum.id = dngtaiid;
					dongtaiPCTNum.pNum = praisenum;
					dongtaiPCTNum.cNum = commentnum;
					dongtaiPCTNum.tNum = transmitnum;
					dongtaiPCTNums.add(dongtaiPCTNum);
				}
			}

			return dongtaiPCTNums;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 进入某用户的资料界面 用户下拉刷新动态的页面 就是请求新的动态 返回6条最新动态的id
	public static Vector<Integer> getUserNewDongtaiIDs(String ph) {

		Connection conn = null;
		Vector<Integer> dongtais = new Vector<Integer>();
		try {
			conn = DBManager.getConnection();
			String sql = "select id from dongtai where userid='" + ph + "' order by id desc limit 0,6";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1));
				dongtais.add(resultSet.getInt(1));

			}
			return dongtais;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtais;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 进入某用户的资料界面 用户上拉刷新动态的页面 就是加载旧的动态 返回6条
	// 从当前的dongtaiid开始往前找6条以前的
	public static Vector<Integer> getUseroldDongtaiIDs(int dongtaiid, String ph) {

		Connection conn = null;
		Vector<Integer> dongtais = new Vector<Integer>();
		try {
			conn = DBManager.getConnection();
			String sql2 = "select count(*) from dongtai where id>'" + dongtaiid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql2);
			if (resultSet.next()) {
				int pos = resultSet.getInt(1);
				int begin = pos + 1;
				int end = 6;
				String sql = "select id from dongtai where userid='" + ph + "' order by id desc limit " + begin + ","
						+ end;
				PreparedStatement pStatement = conn.prepareStatement(sql);

				ResultSet resultSet2 = pStatement.executeQuery();
				while (resultSet2.next()) {
					dongtais.add(resultSet2.getInt(1));
				}

				return dongtais;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtais;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 根据动态id获得动态
	public static Dongtai getDongtaiByDTID(int id, String ph) {
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "select * from dongtai where id='" + id + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {

				Dongtai dongtai = new Dongtai();
				dongtai.setId(resultSet.getInt(1));
				dongtai.setType(resultSet.getString(2));
				dongtai.setSdid(resultSet.getString(3));
				User user = UserDBService.getUserInfoByPhonenumber(resultSet.getString(3));
				if (user != null) {
					dongtai.setSdname(user.getNickname());
					dongtai.setSdic(user.getIcon());
					dongtai.setSdschname(user.getSchoolname());
				}

				dongtai.setContent(resultSet.getString(4));
				dongtai.setImnum(resultSet.getInt(5));

				String fildids = resultSet.getString(6);
				if (fildids != null) {
					fildids = fildids.trim();
				}
				if (fildids != null && !fildids.equals("")) {
					String[] strings = fildids.split(" ");
					for (int i = 0; i < strings.length; ++i) {
						dongtai.getImph().add(strings[i]);
					}
				}
				dongtai.setTime(resultSet.getLong(7));
				dongtai.setPranum(resultSet.getInt(8));
				dongtai.setComnum(resultSet.getShort(9));
				dongtai.setTransnum(resultSet.getInt(10));

				sql = "select id from dongtaimsg where dongtaiid=" + id + " and userid='" + ph + "' and type='praise'";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					dongtai.setPra(true);
				}
				return dongtai;

			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户下拉刷新动态的页面 就是请求新的动态 返回6条最新动态的id
	public static Vector<Integer> getnewDongtaiIDs() {

		Connection conn = null;
		Vector<Integer> dongtais = new Vector<Integer>();
		try {
			conn = DBManager.getConnection();
			String sql = "select id from dongtai order by id desc limit 0,6";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {

				dongtais.add(resultSet.getInt(1));

			}
			return dongtais;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtais;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户上拉刷新动态的页面 就是加载旧的动态 返回6条
	// 从当前的dongtaiid开始往前找6条以前的
	public static Vector<Integer> getoldDongtaiIDs(int dongtaiid) {

		Connection conn = null;
		Vector<Integer> dongtais = new Vector<Integer>();
		try {
			conn = DBManager.getConnection();
			String sql2 = "select count(*) from dongtai where id>'" + dongtaiid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql2);
			if (resultSet.next()) {
				int pos = resultSet.getInt(1);
				int begin = pos + 1;
				int end = 6;
				String sql = "select id from dongtai order by id desc limit " + begin + "," + end;
				PreparedStatement pStatement = conn.prepareStatement(sql);

				ResultSet resultSet2 = pStatement.executeQuery();
				while (resultSet2.next()) {
					dongtais.add(resultSet2.getInt(1));
				}

				return dongtais;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtais;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 根据动态消息的id获得动态消息的基本内容
	public static DongtaiMsg getDongtaiMsgByDTID(int id) {
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "select * from dongtaimsg where id='" + id + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {

				DongtaiMsg dongtai = new DongtaiMsg();
				dongtai.msgid = (resultSet.getInt(1));
				dongtai.dongtaiid = (resultSet.getInt(2));
				String userid = resultSet.getString(3);
				dongtai.userid = (userid);
				String type = resultSet.getString(4);
				dongtai.type = type;
				int commentid = resultSet.getInt(5);
				dongtai.commentid = (commentid);
				dongtai.time = (resultSet.getLong(6));
				dongtai.msg = resultSet.getString(7);
				User user = UserDBService.getUserInfoByPhonenumber(userid);
				if (user != null) {
					dongtai.username = user.getNickname();
					// dongtai.usericon=user.getIcon();
					dongtai.schoolname = user.getSchoolname();
					dongtai.sex = user.getSex();
				}
				if (type.equals("tocomment")) {
					sql = "select userid from dongtaimsg where id='" + commentid + "'";
					ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
					if (resultSet2.next()) {
						String userid2 = resultSet.getString(1);
						dongtai.becommenteduserid = userid2;
						sql = "select nickname from users where phonenumber='" + userid2 + "'";
						ResultSet resultSet3 = conn.createStatement().executeQuery(sql);
						if (resultSet3.next()) {
							String name = resultSet3.getString(1);
							dongtai.becommentedusername = name;
						}
					}
				}

				return dongtai;

			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户下拉刷新动态消息的页面 就是请求新的动态消息 返回6条最新动态消息的id
	public static Vector<Integer> getnewDongtaiMsgIDs(String ph) {

		Connection conn = null;
		Vector<Integer> dongtais = new Vector<Integer>();
		try {
			conn = DBManager.getConnection();
			String sql = "select id from dongtaimsg where reciverid='" + ph + "' order by id desc limit 0,6";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {

				dongtais.add(resultSet.getInt(1));

			}
			return dongtais;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtais;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户上拉刷新动态消息的页面 就是加载旧的动态消息 返回6条动态消息的id
	// 从当前的dongtaiid开始往前找6条以前的s
	public static Vector<Integer> getoldDongtaiMsgIDs(int dongtaiid, String ph) {

		Connection conn = null;
		Vector<Integer> dongtais = new Vector<Integer>();
		try {
			conn = DBManager.getConnection();
			String sql = "select id from dongtaimsg where reciverid='" + ph + "' order by id desc limit '" + dongtaiid
					+ 1 + "','" + dongtaiid + 7 + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				dongtais.add(resultSet.getInt(1));
			}
			return dongtais;
		} catch (Exception e) {
			e.printStackTrace();
			return dongtais;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 进入某动态的所有评论界面 刚开始要刷新 返回根评论总共10条 大的评论下最多回执3条
	public static Vector<DTComRoot> getNewComByDongtaiId(int id) {

		Connection conn = null;
		Vector<DTComRoot> dtComRoots = new Vector<DTComRoot>();
		try {
			conn = DBManager.getConnection();
			String sql = "select * from dongtaimsg where dongtaiid='" + id
					+ "' and type='todongtai' order by id desc limit 0,10";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				DTComRoot dtComRoot = new DTComRoot();
				dtComRoot.setDtid(resultSet.getInt(2));
				dtComRoot.setRootcomid(resultSet.getInt(10));

				dtComRoot.setRootuid(resultSet.getString(3));
				sql = "select nickname from users where phonenumber='" + resultSet.getString(3) + "'";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					dtComRoot.setRootuname(resultSet2.getString(1));
				}
				dtComRoot.setMsg(resultSet.getString(8));
				dtComRoot.setTime(resultSet.getLong(6));
				dtComRoot.setRootcomnum(resultSet.getInt(11));
				dtComRoot.setPraisenum(resultSet.getInt(12));
				dtComRoots.add(dtComRoot);
			}

			for (int i = 0; i < dtComRoots.size(); ++i) {
				int rootcomid = dtComRoots.get(i).getRootcomid();
				sql = "select id,userid,msg,time from dongtaimsg where rootcommentid='" + rootcomid
						+ "' order by id desc limit 0,3";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					DTComCh dtComCh = new DTComCh();
					dtComCh.setComid(resultSet2.getInt(1));
					dtComCh.setUid(resultSet2.getString(2));
					dtComCh.setMsg(resultSet2.getString(3));
					dtComCh.setTime(resultSet2.getLong(4));

					sql = "select nickname from users where phonenumber='" + resultSet2.getString(2) + "'";
					ResultSet resultSet3 = conn.createStatement().executeQuery(sql);
					if (resultSet3.next()) {
						dtComCh.setUname(resultSet3.getString(1));
					}
					dtComRoots.get(i).getDtComChs().addElement(dtComCh);
				}

			}

			return dtComRoots;
		} catch (Exception e) {
			e.printStackTrace();
			return dtComRoots;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 进入某动态的所有评论界面 上拉刷新 返回根评论总共10条 大的评论下最多回执3条
	public static Vector<DTComRoot> getOldComByDongtaiId(int dtid, int comid) {
		Connection conn = null;
		Vector<DTComRoot> dtComRoots = new Vector<DTComRoot>();
		try {
			conn = DBManager.getConnection();
			String sql = "select * from dongtaimsg where dongtaiid='" + dtid
					+ "' and type='todongtai' order by id desc limit '" + comid + 1 + "','" + comid + 10 + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				DTComRoot dtComRoot = new DTComRoot();
				dtComRoot.setDtid(resultSet.getInt(2));
				dtComRoot.setRootcomid(resultSet.getInt(10));

				dtComRoot.setRootuid(resultSet.getString(3));
				sql = "select nickname from users where phonenumber='" + resultSet.getString(3) + "'";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					dtComRoot.setRootuname(resultSet2.getString(1));
				}
				dtComRoot.setMsg(resultSet.getString(8));
				dtComRoot.setTime(resultSet.getLong(6));
				dtComRoot.setRootcomnum(resultSet.getInt(11));
				dtComRoot.setPraisenum(resultSet.getInt(12));
				dtComRoots.add(dtComRoot);
			}

			for (int i = 0; i < dtComRoots.size(); ++i) {
				int rootcomid = dtComRoots.get(i).getRootcomid();
				sql = "select id,userid,msg,time from dongtaimsg where rootcommentid='" + rootcomid
						+ "' order by id desc limit 0,3";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					DTComCh dtComCh = new DTComCh();
					dtComCh.setComid(resultSet2.getInt(1));
					dtComCh.setUid(resultSet2.getString(2));
					dtComCh.setMsg(resultSet2.getString(3));
					dtComCh.setTime(resultSet2.getLong(4));

					sql = "select nickname from users where phonenumber='" + resultSet2.getString(2) + "'";
					ResultSet resultSet3 = conn.createStatement().executeQuery(sql);
					if (resultSet3.next()) {
						dtComCh.setUname(resultSet3.getString(1));
					}
					dtComRoots.get(i).getDtComChs().addElement(dtComCh);
				}

			}

			return dtComRoots;
		} catch (Exception e) {
			e.printStackTrace();
			return dtComRoots;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}
