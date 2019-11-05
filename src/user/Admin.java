package user;

import database.ConnectDB;

import java.sql.ResultSet;
import java.sql.Statement;

public class Admin extends User {
    String acct;
    String pwd;
    String id;
    private String queryString;
    private Statement stmt;
    private ResultSet rSet;

    public Admin(){

    }
    public Admin(String acct, String pwd, String id){
        this.acct = acct;
        this.pwd = pwd;
        this.id = id;
    }

    /* showRecommendList
    无参，返回搜索结果
    显示推荐书籍目录 其中包含书籍名、出版社、作者、ISBN、出版年份、语言、推荐信息
     */
    public ResultSet showRecommendList(){
        stmt = ConnectDB.connect();
        queryString = "select book_name, publisher, author, isbn, publish_year, lang, reco_info" +
                "from recommend_record_table where info_state = 'N'";
        rSet = ConnectDB.search(queryString);

        try{
            while(rSet.next()){
                for(int i = 1; i <= 7; i++){
                    System.out.print(rSet.getString(i));
                    if(i != 7) System.out.print(" ");
                    else System.out.println();
                }
            }
        }catch (Exception e){

        }
        return rSet;
    }

    /* modifyRecommendList(String,int) 用于修改推荐书籍列表中的信息处理状态
    String id传入处理该信息的管理员账号id
    String state传入一个被修改的数据，N未处理，F已处理但不通过，T已处理且通过，O已加入库中
    int isbn传入被修改书籍的ISBN信息，以便搜索结果
     */
    public int modifyRecommendList(String state,int isbn){
        java.util.Date date = new java.util.Date();                 // 获取一个Date对象
        java.sql.Timestamp timeStamp
                = new java.sql.Timestamp(date.getTime());        // 将日期时间转换为数据库中的timestamp类型

        //更新信息状态、管理员ID、处理时间
        queryString = "update recommend_record_table set info_state = " + state + ", admin_id = " + id +
            "op_date = " + timeStamp + " where isbn = " + isbn;

        try{
            stmt.executeQuery(queryString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /* int deleteBook(String, String)
    String bid 书籍id
    String note 书籍删除备注
     */
    public int deleteBook(String bid, String note){
        //删除书籍信息表数据
        queryString = "delete from book_info_table where book_id = " + bid;
        try{
            stmt.executeQuery(queryString);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        //将操作记录到书籍管理记录表中
        queryString = "select count(1) from bm_record_table";       // 查询书籍管理记录表行数
        String op_id;
        try{
            stmt.executeQuery(queryString);
            op_id = "D" + rSet.getString(1);     // 设置书籍删除编号
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        queryString = "insert into bm_record_table (op_id, type, book_id, admin_id, notes) " +      //插入语句
                "values (" + op_id + ", D , " + bid + " , " + id + " , " + note;
        try{
            stmt.executeQuery(queryString);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    /* public int deleteBook(String, String, String)
    String bid 书籍id
    String name 书籍名称
    String note 书籍删除备注
     */
    public int deleteBook(String name, String bid, String note){
        queryString = "delete from book_info_table where book_name = " + name + " book_id = " + bid;
        try{
            stmt.executeQuery(queryString);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        //将操作记录到书籍管理记录表中
        String op_id;
        try{
            rSet = stmt.executeQuery("select count(1) from bm_record_table");   // 查询书籍管理记录表行数
            op_id = "D" + Integer.parseInt(rSet.getString(1))+1;     // 设置书籍删除编号
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        queryString = "insert into bm_record_table (op_id, type, book_id, admin_id, notes) " +      //插入语句
                "values (" + op_id + ", D , " + bid + " , " + id + " , " + note;
        try{
            stmt.executeQuery(queryString);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    /* addBook(String[])
    String str[] 字符串数组，用于存储书籍一系列信息
     */
    public int addBook(String str[]){
        queryString = "insert into book_info_table (book_name, book_id, cat_id, category, publisher, author," +
                "isbn, publish_year, long_time, book_state)" +
                "values (" + str[0] +","+ str[1] +","+ str[2] +","+ str[3] +","+ str[4] +","+ str[5] +","+
                str[6] +","+ str[7] +","+ str[8] +", 'N') ";

        try{
            stmt.executeQuery(queryString);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /* suspendBorrowBook(int bid)
        暂停书籍借阅，将未被借出的书籍暂停借阅，字段改为S
        int bid 传入需要暂停借阅的书籍id
     */
    public int suspendBorrowBook(int bid){
        queryString = "select book_state from book_info_table where book_id = " + bid;
        try{
            rSet = stmt.executeQuery(queryString);
            if(rSet.next() && rSet.getString(1).equalsIgnoreCase("N")){
                stmt.executeQuery("update book_info_table set book_state = 'S' where book_id" + bid);

                //添加操作记录
                stmt.executeQuery("select count(1) from bm_record_table");
                String op_id = "P" + Integer.parseInt(rSet.getString(1))+1;
                stmt.executeQuery("insert into bm_record_table (op_id, type, book_id, admin_id) " +      //插入语句
                        "values (" + op_id + ", D , " + bid + " , " + id);
            }
            else {
                System.out.println("No Found Book");
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /* resumeBorrowBook(int bid)
        恢复书籍借阅，将未被借出的书籍恢复借阅，字段改为N
        int bid 传入需要暂停借阅的书籍id
     */
    public int resumeBorrowBook(int bid){
        queryString = "select book_state from book_info_table where book_id = " + bid;
        try{
            rSet = stmt.executeQuery(queryString);
            if(rSet.next() && rSet.getString(1).equalsIgnoreCase("N")){
                stmt.executeQuery("update book_info_table set book_state = 'N' where book_id" + bid);

                //添加操作记录
                stmt.executeQuery("select count(1) from bm_record_table");
                String op_id = "R" + Integer.parseInt(rSet.getString(1))+1;
                stmt.executeQuery("insert into bm_record_table (op_id, type, book_id, admin_id) " +      //插入语句
                        "values (" + op_id + ", D , " + bid + " , " + id);
            }
            else {
                System.out.println("No Found Book");
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /* modifyBookInfo(int bid)
    用于修改书籍基本信息，仅支持修改分类编号以及书籍大类
    若需要修改其他信息，建议通过addBook()方法进行新书籍添加，并删除原书籍信息
     */
    public int modifyBookInfo(int bid){
        try{
            rSet = stmt.executeQuery("select cat_id, category from book_info_table where book_id = " + bid);
            if(rSet.next()){
                String newCat_id = new String();
                String newCategory = new String();
                stmt.executeQuery("update book_info_table set cat_id = " + newCat_id + ", category = " + newCategory+ " where book_id" + bid);

                //添加操作记录
                stmt.executeQuery("select count(1) from bm_record_table");
                String op_id = "M" + Integer.parseInt(rSet.getString(1))+1;
                stmt.executeQuery("insert into bm_record_table (op_id, type, book_id, admin_id) " +      //插入语句
                        "values (" + op_id + ", M , " + bid + " , " + id);
            }
            else {
                System.out.println("No Found Book");
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

}
