package com.zelin;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.session.ClientSession;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {

    //连接MongoDB
    private MongoCollection<Document> getConnection() {
        //创建MongoDB客户端
        MongoClient mongoClient = new MongoClient("192.168.25.130");
        //连接指定的数据库
        MongoDatabase db = mongoClient.getDatabase("commentdb");
        //通过数据库获得指定的Collection（表）
        MongoCollection<Document> collection = db.getCollection("comment");
        return collection;
    }

    //打印文档信息
    private void printDocuments(FindIterable<Document> documents) {
        for (Document doc : documents) {
            Set<Map.Entry<String, Object>> entries = doc.entrySet();
            System.out.println("############################################");
            for (Map.Entry<String,Object> entry : entries) {
                System.out.println(entry.getKey() + ": " + entry.getValue().toString());
            }
        }
        System.out.println("############################################");
    }

    //添加文档
    @Test
    public void addSplit() {
        MongoCollection<Document> collection = getConnection();
        Document document = new Document();
        document.put("content","不得不吐槽公司刚调整的吃饭作息时间");
        document.put("publishtime","2019-09-19T10:34:23.224Z");
        document.put("userid","4569700");
        document.put("nickname","职场菜鸟");
        document.put("visits",3475869);
        document.put("thumbup",2234356);
        document.put("share",135);
        document.put("comment",1134476);
        document.put("state","1");
        document.put("parentid","1324678");
        collection.insertOne(document);
    }

    //添加文档
    @Test
    public void addComment() {
        MongoCollection<Document> collection = getConnection();
        Document document = new Document();
        document.put("articleid","聆听您的教诲");
        document.put("userid","2457828");
        document.put("parentid","0");
        document.put("publishdate","2019-09-19T10:34:23.224Z");
        collection.insertOne(document);
    }

    //添加多个文档
    @Test
    public void addMany() {
        MongoCollection<Document> collection = getConnection();
        List<Document> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Document document = new Document();
            document.put("articleid","腻害呐~~");
            document.put("userid",new Random().nextInt(1000000)%i + "");
            document.put("parentid","0");
            document.put("publishdate","2019-09-19T07:39:36.000+0000");
            list.add(document);
        }
        collection.insertMany(list);
    }

    //查询所有数据文档
    @Test
    public void findAll() {
        MongoCollection<Document> collection = getConnection();
        FindIterable<Document> documents = collection.find();
        printDocuments(documents);
    }

    //精确查询
    @Test
    public void accurateQuery1() {
        MongoCollection<Document> collection = getConnection();
        Bson bson = new BasicDBObject("nickname","贱贱");
        //Document doc = new Document("state","1");
        FindIterable<Document> documents = collection.find(bson);
        printDocuments(documents);
    }

    //模糊查询1
    @Test
    public void fuzzyQuery1() {
        MongoCollection<Document> collection = getConnection();
        Map<String,Object> map = new HashMap<>();
        map.put("thumbup",new BasicDBObject("$gt",1000));
        map.put("state","1");
        map.put("content",new BasicDBObject("$regex","公司"));
        map.put("comment",new BasicDBObject("$lt",20000));
        Bson bson = new BasicDBObject(map);
        FindIterable<Document> documents = collection.find(bson);
        printDocuments(documents);
    }

    //模糊查询2
    @Test
    public void fuzzyQuery2(){
        MongoCollection<Document> collection = getConnection();
        Document doc = new Document("visits",new Document("$gt",1000));
        doc.put("content",new Document("$regex","公司"));
        doc.put("content",new Document("$regex","中秋"));
        doc.put("comment",new Document("$gt",500));
        FindIterable<Document> documents = collection.find(doc);
        printDocuments(documents);
    }


    //模糊查询3
    @Test
    public void fuzzyQuery3() {
        MongoCollection<Document> collection = getConnection();
        List<Document> list = new ArrayList<>();
        list.add(new Document("content",new Document("$regex","公司")));
        list.add(new Document("nickname",new Document("$regex","猫")));
        list.add(new Document("comment",new Document("$gt",10)));
        list.add(new Document("comment",new Document("$lt",10000)));
        list.add(new Document("state","1"));
        Bson bson = new BasicDBObject("$and",list);
        FindIterable<Document> documents = collection.find(bson);
        printDocuments(documents);
    }

    //删除所有文档
    @Test
    public void removeAll() {
        MongoCollection<Document> collection = getConnection();
        collection.drop();
    }

}
