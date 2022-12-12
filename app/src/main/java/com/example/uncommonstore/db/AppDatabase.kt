package com.example.uncommonstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uncommonstore.event.db.EventDao
import com.example.uncommonstore.event.db.EventEntity
import com.example.uncommonstore.payment.db.CardDao
import com.example.uncommonstore.payment.db.CardEntity
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
/*****************************************************
 * @function : ProductEntity
 * @author : 정구현(Room DB 연결, Product), 김나형(Event), 심지연(Card)
 * @Date : 2022.12.04 생성
 *****************************************************/
@Database(entities = arrayOf(ProductEntity::class, EventEntity::class, CardEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    //2022.12.04 정구현 추가 : 상품 테이블 추가
    abstract fun ProductDao() : ProductDao

    //2022.12.05 김나형 추가 : Event 테이블
    abstract fun EventDao() : EventDao

    // 2022.12.06 심지연 추가: room Card 테이블
    abstract fun CardDao() : CardDao

    // 2022.12.04 정구현 추가 : room databse 연결
    companion object{
        val databaseName = "db"
        var appDatabase : AppDatabase? = null
        fun getInstance(context: Context):AppDatabase?{
            if(appDatabase == null){
                synchronized(AppDatabase::class){
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        databaseName
                    ).addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //2022.12.04 정구현 추가 : 상품 샘플 데이터 추가
                            fillInDb(context.applicationContext)
                            // 2022.12.05 김나형 추가 : event 데이터 추가
                            fillInEventDb(context.applicationContext)
                        }
                    }).build()
                }
            }

            return appDatabase
        }
        // 2022.12.04 정구현 추가 : Product 샘플 데이터 추가 함수
        fun fillInDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.ProductDao().insertProduct(
                    PRODUCT_DATA
                )
            }
        }

        // 2022.12.05 김나형 추가 : Event 샘플 데이터 추가 함수
        fun fillInEventDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.EventDao().insertEvent(
                    EVENT_DATA
                )
            }
        }
    }
}
//2022.12.04 정구현 추가 : Product 기본 데이터 추가
private val PRODUCT_DATA = arrayListOf(
    ProductEntity(null, "[최고심] [그립톡] 젤리토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206120577-5aa93800-38fd-491c-a836-c6e2f8b5454a.jpg,https://user-images.githubusercontent.com/70796139/206120581-176f12b9-d27d-440b-9c3f-152cf2576fd6.jpg,https://user-images.githubusercontent.com/70796139/206120596-a8501eb9-c585-4ae0-bace-ee30ce268362.jpg",
        "https://user-images.githubusercontent.com/70796139/206120613-2ffa5dea-78ec-4bcd-a43a-fe23a364a99c.jpg,https://user-images.githubusercontent.com/70796139/206120625-072bbeb6-c41d-411f-85f5-e560d94dbe87.jpg,https://user-images.githubusercontent.com/70796139/206120630-f18b9ac1-17ac-4e4c-8e98-613def931d83.jpg,https://user-images.githubusercontent.com/70796139/206120653-e4299aea-7587-41d8-95b0-d68a7203cfe9.jpg",
        "[최고심] [그립톡] 젤리토끼"),
    ProductEntity(null, "[최고심] [메모지] 초록순자", "16,000",9,
        "https://user-images.githubusercontent.com/70796139/206522694-77d8be80-481f-4580-967d-69bc746a434c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522713-8bfba543-e7b2-479d-9f52-3fece764a8b6.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522755-16b8bbe3-7e2d-4059-9bba-641aa3a285bb.jpg",
        "https://user-images.githubusercontent.com/70796139/206522830-53c7fde5-1a4d-447d-aaf8-94580015ce86.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522856-3a395837-8c66-4363-9849-7cae1ad5fee3.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522882-07c14a84-4527-4f5c-afeb-63ad36575313.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522938-315590d1-3cc4-4a05-8b3d-fc5ec7da64de.jpg",
        "최고심 메모지 6종\n긍정 에너지 가득한! 행복가득 메모지 6종을 만나보세요!"),
    ProductEntity(null, "[최고심] [패브릭포스터] 하기싫으면 하지말자", "16,000",8,
        "https://user-images.githubusercontent.com/70796139/206523821-7f434627-fa47-4ed4-90b0-27e403880d07.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523836-fb2c2ee6-95b5-4761-aa2c-d98ae8724c4e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523881-6b43a03a-f3ba-47b9-a65b-5d3b0f19ff22.jpg",
        "https://user-images.githubusercontent.com/70796139/206523901-34d145b3-2596-4c7d-ab74-c717987cb187.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523959-eaf67e73-73a1-476d-9367-9dfe343bd2a1.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523980-a3997731-e216-4f10-9ca0-7dd168a27b44.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524002-6550183c-cc8a-4c73-ac8a-7a02ee4392b6.jpg",
        "최고심 패브릭 포스터 4종"),
    ProductEntity(null, "[최고심] [컵] 할수있따", "14,000",15,
        "https://user-images.githubusercontent.com/70796139/206524436-07b6eefd-673d-4847-8ff4-6ba23ba54463.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524457-752b44a5-9dec-4638-a189-e4c45e4a142d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524472-1c2728e6-8dc9-4210-89d3-8c13dadc1c7e.jpg",
        "https://user-images.githubusercontent.com/70796139/206524511-bf3be00c-bc86-497c-ab2f-e8cbf5849f68.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524531-22722c51-eaf4-4655-837d-8c70a599788e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524551-94a990c6-0417-4181-b0dd-fb43e8473003.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524610-43cfc46f-e1ba-4f72-860e-a67c72a7e0e1.jpg",
        "최고심 글라스 5종"),
    ProductEntity(null, "[최고심] [노트북 파우치] 행복과 행운 15인치", "51,000",16,
        "https://user-images.githubusercontent.com/70796139/206524958-4149b956-d9b8-4cf3-964a-7411f512bc95.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524970-eb5b5c06-2925-4022-88df-c0a6edb0605c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524983-1aaaa86f-e539-40ee-8b3b-31f70761a9d9.jpg",
        "https://user-images.githubusercontent.com/70796139/206524999-022b51a7-ac75-4f44-89b4-d9c7af936171.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525022-52aabbce-9b8b-4565-901d-716d4ef8783d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525098-d51a66b0-b2cc-4d31-9836-6bd17c7e7925.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525106-d2244a1a-b0f0-42f6-bf1c-987f31ca8fd3.jpg",
        "최고심 폭신폭신 노트북 파우치 2종 \n\n"+
                "13인치, 15인치"),
    ProductEntity(null, "[최고심] [그립톡] 안경곰", "15,000",3,
        "https://user-images.githubusercontent.com/70796139/206525561-fb56bb52-e88c-47f0-ab32-edd1844234cd.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525572-d4ca327f-3fd5-4839-8618-9d5f0ec59385.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525592-1a697639-9501-4bc7-b1c0-422e69912cad.jpg",
        "https://user-images.githubusercontent.com/70796139/206525621-e219fc2c-0f8a-41d4-a5a1-cd0725f2135c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525634-1313c015-cc95-4d09-94a2-d39c0be55cb5.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525651-bff5bade-0462-41eb-8e3d-ab1fd3dcf9ce.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525664-05644766-9e9d-4eb2-a255-525c7302d55f.jpg",
        "[그립톡] 안경곰"),
    ProductEntity(null, "[최고심] [인형] 사랑의 토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206526047-67c8c35c-0d8f-4828-a576-0c56000ab729.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526142-0c54a2a1-9636-4018-a427-0c883e217769.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526130-32de82aa-e7c1-4058-ad8f-8b953f86ad7d.jpg",
        "https://user-images.githubusercontent.com/70796139/206526321-3953298a-f2be-400a-af16-39fbd82f35aa.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526352-aff3e1d2-da3c-4833-9bca-9f95e72560e7.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526359-4ea8d9b9-52fb-4a6b-847a-7aeaa8511fe2.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526367-4915537c-3b91-43d4-a4c4-9423bbaf08ae.jpg",
        "최고심 사랑의 토끼!"),
    ProductEntity(null, "[최고심] [그립톡] 젤리토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206120577-5aa93800-38fd-491c-a836-c6e2f8b5454a.jpg,https://user-images.githubusercontent.com/70796139/206120581-176f12b9-d27d-440b-9c3f-152cf2576fd6.jpg,https://user-images.githubusercontent.com/70796139/206120596-a8501eb9-c585-4ae0-bace-ee30ce268362.jpg",
        "https://user-images.githubusercontent.com/70796139/206120613-2ffa5dea-78ec-4bcd-a43a-fe23a364a99c.jpg,https://user-images.githubusercontent.com/70796139/206120625-072bbeb6-c41d-411f-85f5-e560d94dbe87.jpg,https://user-images.githubusercontent.com/70796139/206120630-f18b9ac1-17ac-4e4c-8e98-613def931d83.jpg,https://user-images.githubusercontent.com/70796139/206120653-e4299aea-7587-41d8-95b0-d68a7203cfe9.jpg",
        "[최고심] [그립톡] 젤리토끼"),
    ProductEntity(null, "[최고심] [메모지] 초록순자", "16,000",9,
        "https://user-images.githubusercontent.com/70796139/206522694-77d8be80-481f-4580-967d-69bc746a434c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522713-8bfba543-e7b2-479d-9f52-3fece764a8b6.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522755-16b8bbe3-7e2d-4059-9bba-641aa3a285bb.jpg",
        "https://user-images.githubusercontent.com/70796139/206522830-53c7fde5-1a4d-447d-aaf8-94580015ce86.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522856-3a395837-8c66-4363-9849-7cae1ad5fee3.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522882-07c14a84-4527-4f5c-afeb-63ad36575313.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522938-315590d1-3cc4-4a05-8b3d-fc5ec7da64de.jpg",
        "최고심 메모지 6종\n긍정 에너지 가득한! 행복가득 메모지 6종을 만나보세요!"),
    ProductEntity(null, "[최고심] [패브릭포스터] 하기싫으면 하지말자", "16,000",8,
        "https://user-images.githubusercontent.com/70796139/206523821-7f434627-fa47-4ed4-90b0-27e403880d07.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523836-fb2c2ee6-95b5-4761-aa2c-d98ae8724c4e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523881-6b43a03a-f3ba-47b9-a65b-5d3b0f19ff22.jpg",
        "https://user-images.githubusercontent.com/70796139/206523901-34d145b3-2596-4c7d-ab74-c717987cb187.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523959-eaf67e73-73a1-476d-9367-9dfe343bd2a1.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523980-a3997731-e216-4f10-9ca0-7dd168a27b44.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524002-6550183c-cc8a-4c73-ac8a-7a02ee4392b6.jpg",
        "최고심 패브릭 포스터 4종"),
    ProductEntity(null, "[최고심] [컵] 할수있따", "14,000",15,
        "https://user-images.githubusercontent.com/70796139/206524436-07b6eefd-673d-4847-8ff4-6ba23ba54463.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524457-752b44a5-9dec-4638-a189-e4c45e4a142d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524472-1c2728e6-8dc9-4210-89d3-8c13dadc1c7e.jpg",
        "https://user-images.githubusercontent.com/70796139/206524511-bf3be00c-bc86-497c-ab2f-e8cbf5849f68.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524531-22722c51-eaf4-4655-837d-8c70a599788e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524551-94a990c6-0417-4181-b0dd-fb43e8473003.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524610-43cfc46f-e1ba-4f72-860e-a67c72a7e0e1.jpg",
        "최고심 글라스 5종"),
    ProductEntity(null, "[최고심] [노트북 파우치] 행복과 행운 15인치", "51,000",16,
        "https://user-images.githubusercontent.com/70796139/206524958-4149b956-d9b8-4cf3-964a-7411f512bc95.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524970-eb5b5c06-2925-4022-88df-c0a6edb0605c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524983-1aaaa86f-e539-40ee-8b3b-31f70761a9d9.jpg",
        "https://user-images.githubusercontent.com/70796139/206524999-022b51a7-ac75-4f44-89b4-d9c7af936171.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525022-52aabbce-9b8b-4565-901d-716d4ef8783d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525098-d51a66b0-b2cc-4d31-9836-6bd17c7e7925.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525106-d2244a1a-b0f0-42f6-bf1c-987f31ca8fd3.jpg",
        "최고심 폭신폭신 노트북 파우치 2종 \n\n"+
                "13인치, 15인치"),
    ProductEntity(null, "[최고심] [그립톡] 안경곰", "15,000",3,
        "https://user-images.githubusercontent.com/70796139/206525561-fb56bb52-e88c-47f0-ab32-edd1844234cd.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525572-d4ca327f-3fd5-4839-8618-9d5f0ec59385.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525592-1a697639-9501-4bc7-b1c0-422e69912cad.jpg",
        "https://user-images.githubusercontent.com/70796139/206525621-e219fc2c-0f8a-41d4-a5a1-cd0725f2135c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525634-1313c015-cc95-4d09-94a2-d39c0be55cb5.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525651-bff5bade-0462-41eb-8e3d-ab1fd3dcf9ce.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525664-05644766-9e9d-4eb2-a255-525c7302d55f.jpg",
        "[그립톡] 안경곰"),
    ProductEntity(null, "[최고심] [인형] 사랑의 토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206526047-67c8c35c-0d8f-4828-a576-0c56000ab729.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526142-0c54a2a1-9636-4018-a427-0c883e217769.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526130-32de82aa-e7c1-4058-ad8f-8b953f86ad7d.jpg",
        "https://user-images.githubusercontent.com/70796139/206526321-3953298a-f2be-400a-af16-39fbd82f35aa.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526352-aff3e1d2-da3c-4833-9bca-9f95e72560e7.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526359-4ea8d9b9-52fb-4a6b-847a-7aeaa8511fe2.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526367-4915537c-3b91-43d4-a4c4-9423bbaf08ae.jpg",
        "최고심 사랑의 토끼!"),
    ProductEntity(null, "[최고심] [그립톡] 젤리토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206120577-5aa93800-38fd-491c-a836-c6e2f8b5454a.jpg,https://user-images.githubusercontent.com/70796139/206120581-176f12b9-d27d-440b-9c3f-152cf2576fd6.jpg,https://user-images.githubusercontent.com/70796139/206120596-a8501eb9-c585-4ae0-bace-ee30ce268362.jpg",
        "https://user-images.githubusercontent.com/70796139/206120613-2ffa5dea-78ec-4bcd-a43a-fe23a364a99c.jpg,https://user-images.githubusercontent.com/70796139/206120625-072bbeb6-c41d-411f-85f5-e560d94dbe87.jpg,https://user-images.githubusercontent.com/70796139/206120630-f18b9ac1-17ac-4e4c-8e98-613def931d83.jpg,https://user-images.githubusercontent.com/70796139/206120653-e4299aea-7587-41d8-95b0-d68a7203cfe9.jpg",
        "[최고심] [그립톡] 젤리토끼"),
    ProductEntity(null, "[최고심] [메모지] 초록순자", "16,000",9,
        "https://user-images.githubusercontent.com/70796139/206522694-77d8be80-481f-4580-967d-69bc746a434c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522713-8bfba543-e7b2-479d-9f52-3fece764a8b6.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522755-16b8bbe3-7e2d-4059-9bba-641aa3a285bb.jpg",
        "https://user-images.githubusercontent.com/70796139/206522830-53c7fde5-1a4d-447d-aaf8-94580015ce86.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522856-3a395837-8c66-4363-9849-7cae1ad5fee3.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522882-07c14a84-4527-4f5c-afeb-63ad36575313.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522938-315590d1-3cc4-4a05-8b3d-fc5ec7da64de.jpg",
        "최고심 메모지 6종\n긍정 에너지 가득한! 행복가득 메모지 6종을 만나보세요!"),
    ProductEntity(null, "[최고심] [패브릭포스터] 하기싫으면 하지말자", "16,000",8,
        "https://user-images.githubusercontent.com/70796139/206523821-7f434627-fa47-4ed4-90b0-27e403880d07.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523836-fb2c2ee6-95b5-4761-aa2c-d98ae8724c4e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523881-6b43a03a-f3ba-47b9-a65b-5d3b0f19ff22.jpg",
        "https://user-images.githubusercontent.com/70796139/206523901-34d145b3-2596-4c7d-ab74-c717987cb187.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523959-eaf67e73-73a1-476d-9367-9dfe343bd2a1.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523980-a3997731-e216-4f10-9ca0-7dd168a27b44.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524002-6550183c-cc8a-4c73-ac8a-7a02ee4392b6.jpg",
        "최고심 패브릭 포스터 4종"),
    ProductEntity(null, "[최고심] [컵] 할수있따", "14,000",15,
        "https://user-images.githubusercontent.com/70796139/206524436-07b6eefd-673d-4847-8ff4-6ba23ba54463.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524457-752b44a5-9dec-4638-a189-e4c45e4a142d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524472-1c2728e6-8dc9-4210-89d3-8c13dadc1c7e.jpg",
        "https://user-images.githubusercontent.com/70796139/206524511-bf3be00c-bc86-497c-ab2f-e8cbf5849f68.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524531-22722c51-eaf4-4655-837d-8c70a599788e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524551-94a990c6-0417-4181-b0dd-fb43e8473003.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524610-43cfc46f-e1ba-4f72-860e-a67c72a7e0e1.jpg",
        "최고심 글라스 5종"),
    ProductEntity(null, "[최고심] [노트북 파우치] 행복과 행운 15인치", "51,000",16,
        "https://user-images.githubusercontent.com/70796139/206524958-4149b956-d9b8-4cf3-964a-7411f512bc95.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524970-eb5b5c06-2925-4022-88df-c0a6edb0605c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524983-1aaaa86f-e539-40ee-8b3b-31f70761a9d9.jpg",
        "https://user-images.githubusercontent.com/70796139/206524999-022b51a7-ac75-4f44-89b4-d9c7af936171.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525022-52aabbce-9b8b-4565-901d-716d4ef8783d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525098-d51a66b0-b2cc-4d31-9836-6bd17c7e7925.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525106-d2244a1a-b0f0-42f6-bf1c-987f31ca8fd3.jpg",
        "최고심 폭신폭신 노트북 파우치 2종 \n\n"+
                "13인치, 15인치"),
    ProductEntity(null, "[최고심] [그립톡] 안경곰", "15,000",3,
        "https://user-images.githubusercontent.com/70796139/206525561-fb56bb52-e88c-47f0-ab32-edd1844234cd.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525572-d4ca327f-3fd5-4839-8618-9d5f0ec59385.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525592-1a697639-9501-4bc7-b1c0-422e69912cad.jpg",
        "https://user-images.githubusercontent.com/70796139/206525621-e219fc2c-0f8a-41d4-a5a1-cd0725f2135c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525634-1313c015-cc95-4d09-94a2-d39c0be55cb5.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525651-bff5bade-0462-41eb-8e3d-ab1fd3dcf9ce.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525664-05644766-9e9d-4eb2-a255-525c7302d55f.jpg",
        "[그립톡] 안경곰"),
    ProductEntity(null, "[최고심] [인형] 사랑의 토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206526047-67c8c35c-0d8f-4828-a576-0c56000ab729.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526142-0c54a2a1-9636-4018-a427-0c883e217769.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526130-32de82aa-e7c1-4058-ad8f-8b953f86ad7d.jpg",
        "https://user-images.githubusercontent.com/70796139/206526321-3953298a-f2be-400a-af16-39fbd82f35aa.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526352-aff3e1d2-da3c-4833-9bca-9f95e72560e7.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526359-4ea8d9b9-52fb-4a6b-847a-7aeaa8511fe2.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526367-4915537c-3b91-43d4-a4c4-9423bbaf08ae.jpg",
        "최고심 사랑의 토끼!"),
    ProductEntity(null, "[최고심] [그립톡] 젤리토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206120577-5aa93800-38fd-491c-a836-c6e2f8b5454a.jpg,https://user-images.githubusercontent.com/70796139/206120581-176f12b9-d27d-440b-9c3f-152cf2576fd6.jpg,https://user-images.githubusercontent.com/70796139/206120596-a8501eb9-c585-4ae0-bace-ee30ce268362.jpg",
        "https://user-images.githubusercontent.com/70796139/206120613-2ffa5dea-78ec-4bcd-a43a-fe23a364a99c.jpg,https://user-images.githubusercontent.com/70796139/206120625-072bbeb6-c41d-411f-85f5-e560d94dbe87.jpg,https://user-images.githubusercontent.com/70796139/206120630-f18b9ac1-17ac-4e4c-8e98-613def931d83.jpg,https://user-images.githubusercontent.com/70796139/206120653-e4299aea-7587-41d8-95b0-d68a7203cfe9.jpg",
        "[최고심] [그립톡] 젤리토끼"),
    ProductEntity(null, "[최고심] [메모지] 초록순자", "16,000",9,
        "https://user-images.githubusercontent.com/70796139/206522694-77d8be80-481f-4580-967d-69bc746a434c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522713-8bfba543-e7b2-479d-9f52-3fece764a8b6.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522755-16b8bbe3-7e2d-4059-9bba-641aa3a285bb.jpg",
        "https://user-images.githubusercontent.com/70796139/206522830-53c7fde5-1a4d-447d-aaf8-94580015ce86.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522856-3a395837-8c66-4363-9849-7cae1ad5fee3.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522882-07c14a84-4527-4f5c-afeb-63ad36575313.jpg," +
                "https://user-images.githubusercontent.com/70796139/206522938-315590d1-3cc4-4a05-8b3d-fc5ec7da64de.jpg",
        "최고심 메모지 6종\n긍정 에너지 가득한! 행복가득 메모지 6종을 만나보세요!"),
    ProductEntity(null, "[최고심] [패브릭포스터] 하기싫으면 하지말자", "16,000",8,
        "https://user-images.githubusercontent.com/70796139/206523821-7f434627-fa47-4ed4-90b0-27e403880d07.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523836-fb2c2ee6-95b5-4761-aa2c-d98ae8724c4e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523881-6b43a03a-f3ba-47b9-a65b-5d3b0f19ff22.jpg",
        "https://user-images.githubusercontent.com/70796139/206523901-34d145b3-2596-4c7d-ab74-c717987cb187.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523959-eaf67e73-73a1-476d-9367-9dfe343bd2a1.jpg," +
                "https://user-images.githubusercontent.com/70796139/206523980-a3997731-e216-4f10-9ca0-7dd168a27b44.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524002-6550183c-cc8a-4c73-ac8a-7a02ee4392b6.jpg",
        "최고심 패브릭 포스터 4종"),
    ProductEntity(null, "[최고심] [컵] 할수있따", "14,000",15,
        "https://user-images.githubusercontent.com/70796139/206524436-07b6eefd-673d-4847-8ff4-6ba23ba54463.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524457-752b44a5-9dec-4638-a189-e4c45e4a142d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524472-1c2728e6-8dc9-4210-89d3-8c13dadc1c7e.jpg",
        "https://user-images.githubusercontent.com/70796139/206524511-bf3be00c-bc86-497c-ab2f-e8cbf5849f68.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524531-22722c51-eaf4-4655-837d-8c70a599788e.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524551-94a990c6-0417-4181-b0dd-fb43e8473003.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524610-43cfc46f-e1ba-4f72-860e-a67c72a7e0e1.jpg",
        "최고심 글라스 5종"),
    ProductEntity(null, "[최고심] [노트북 파우치] 행복과 행운 15인치", "51,000",16,
        "https://user-images.githubusercontent.com/70796139/206524958-4149b956-d9b8-4cf3-964a-7411f512bc95.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524970-eb5b5c06-2925-4022-88df-c0a6edb0605c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206524983-1aaaa86f-e539-40ee-8b3b-31f70761a9d9.jpg",
        "https://user-images.githubusercontent.com/70796139/206524999-022b51a7-ac75-4f44-89b4-d9c7af936171.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525022-52aabbce-9b8b-4565-901d-716d4ef8783d.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525098-d51a66b0-b2cc-4d31-9836-6bd17c7e7925.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525106-d2244a1a-b0f0-42f6-bf1c-987f31ca8fd3.jpg",
        "최고심 폭신폭신 노트북 파우치 2종 \n\n"+
                "13인치, 15인치"),
    ProductEntity(null, "[최고심] [그립톡] 안경곰", "15,000",3,
        "https://user-images.githubusercontent.com/70796139/206525561-fb56bb52-e88c-47f0-ab32-edd1844234cd.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525572-d4ca327f-3fd5-4839-8618-9d5f0ec59385.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525592-1a697639-9501-4bc7-b1c0-422e69912cad.jpg",
        "https://user-images.githubusercontent.com/70796139/206525621-e219fc2c-0f8a-41d4-a5a1-cd0725f2135c.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525634-1313c015-cc95-4d09-94a2-d39c0be55cb5.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525651-bff5bade-0462-41eb-8e3d-ab1fd3dcf9ce.jpg," +
                "https://user-images.githubusercontent.com/70796139/206525664-05644766-9e9d-4eb2-a255-525c7302d55f.jpg",
        "[그립톡] 안경곰"),
    ProductEntity(null, "[최고심] [인형] 사랑의 토끼", "15,000",10,
        "https://user-images.githubusercontent.com/70796139/206526047-67c8c35c-0d8f-4828-a576-0c56000ab729.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526142-0c54a2a1-9636-4018-a427-0c883e217769.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526130-32de82aa-e7c1-4058-ad8f-8b953f86ad7d.jpg",
        "https://user-images.githubusercontent.com/70796139/206526321-3953298a-f2be-400a-af16-39fbd82f35aa.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526352-aff3e1d2-da3c-4833-9bca-9f95e72560e7.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526359-4ea8d9b9-52fb-4a6b-847a-7aeaa8511fe2.jpg," +
                "https://user-images.githubusercontent.com/70796139/206526367-4915537c-3b91-43d4-a4c4-9423bbaf08ae.jpg",
        "최고심 사랑의 토끼!")

    )

// 2022.12.05 김나형 추가 : Event 기본 데이터 세팅
private val EVENT_DATA = arrayListOf(
    EventEntity(null, "망그러진 곰 X 더 현대 서울 언커먼 스토어", "https://user-images.githubusercontent.com/63277040/205828117-86c948c2-33c8-4d98-9e20-ad5dbe42ac11.PNG",
        "https://user-images.githubusercontent.com/63277040/205915973-a45070ab-23c4-485b-9a5b-a841625b5a63.jpg","https://user-images.githubusercontent.com/63277040/205915976-74e27f7d-3362-4561-91ef-7f71d5909393.jpg","https://user-images.githubusercontent.com/63277040/205915977-6b433f43-df7c-49a6-a4d3-be5ac6fc9c9d.jpg","행사기간 : 11/4(금)~12/22(목)"),
    EventEntity(null, "김미묘 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205842977-6940e6a8-cd7f-4968-9900-de677cc35145.PNG",
        "https://user-images.githubusercontent.com/63277040/205916643-565bffb1-a06e-4abb-8239-d50d150da677.jpg","https://user-images.githubusercontent.com/63277040/205916653-c88c4f49-2ad7-42d5-b649-ca2317b40c52.jpg","https://user-images.githubusercontent.com/63277040/205916656-4987185a-be31-4633-b3ec-83206c3f2bab.jpg","9/7(수) Open!"),
    EventEntity(null, "누누씨 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205853086-460599d8-12dd-43dd-9e61-583c9d7dedfe.PNG",
        "https://user-images.githubusercontent.com/63277040/205916883-c086e8ae-7538-4ca9-b5b1-4af2e0509d3a.jpg","https://user-images.githubusercontent.com/63277040/205916931-ef80a40a-8a06-47d4-96c4-9a45cbbf9e4c.jpg","https://user-images.githubusercontent.com/63277040/205916985-a5f5491b-41ad-427f-8d4b-76536d3490a2.jpg","7월 15일 오픈!"),
    EventEntity(null, "여자아이들 팝업스토어", "https://user-images.githubusercontent.com/63277040/205853154-05d0813a-c54d-4c72-bd50-c3270ac3c143.PNG",
        "https://user-images.githubusercontent.com/63277040/205917203-1b2265d4-7763-4c64-9ccd-57a1ebec5e6f.jpg","https://user-images.githubusercontent.com/63277040/205917928-23d4b9f9-c379-4a83-9a47-5059e7ca110e.PNG","https://user-images.githubusercontent.com/63277040/205917939-05a46c84-63af-4438-ac90-0466779efd25.jpg","여자아이들의 미니앨범 5집 I love 발매 기념 팝업스토어가 언커먼스토어에서 진행됩니다."),
    EventEntity(null, "PLAY HEENDY DAY !", "https://user-images.githubusercontent.com/63277040/205853217-129fdc0a-5a8f-441b-b05e-6ca2cc504465.PNG",
        "https://user-images.githubusercontent.com/63277040/205916503-9ed9191c-f430-4571-a17c-56f9ae75b317.jpg","https://user-images.githubusercontent.com/63277040/205916505-d9b30d96-3f92-4d56-8f76-0e1b236b3880.jpg","https://user-images.githubusercontent.com/63277040/205916495-d2273062-9bcc-4ff3-a1f5-784c64a0921b.jpg","흰디 굿즈를 특별한 가격에 만나보세요!"),
    EventEntity(null, "김토끼 스튜도 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205853317-5f420c49-1ceb-4655-af7a-f9b7ab5ed61f.PNG",
        "https://user-images.githubusercontent.com/63277040/205917080-2c246956-a7bf-40ea-bb17-aabbdebdbdd1.jpg","https://user-images.githubusercontent.com/63277040/205917074-185e1dc9-263d-4bc7-8fe5-8369962ccbf0.jpg","https://user-images.githubusercontent.com/63277040/205917082-9ff86eb3-f1e9-4d24-8beb-b09442586a2c.jpg","기간 : 6/3 ~ 7/7"),
    EventEntity(null, "바리수 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205853382-f22d236f-267a-4cc8-9a83-112ac0b3fba9.PNG",
        "https://user-images.githubusercontent.com/63277040/205915509-5469f9ed-799d-40ca-b932-ec863418c910.jpg","https://user-images.githubusercontent.com/63277040/205915605-25997bfc-54cd-4431-a56d-180f962e5708.jpg","https://user-images.githubusercontent.com/63277040/205915608-d3f696b2-4cbe-454c-b3a3-033ac241f221.jpg","기간 : 4/22(금) ~ 6/2(목)"),
    EventEntity(null, "바잇미 팝업스토어", "https://user-images.githubusercontent.com/63277040/205853444-1ff5348d-18e0-4b2e-a12f-c14f1e461927.PNG",
        "https://user-images.githubusercontent.com/63277040/205916361-77ce1701-f1d5-4631-86a1-1db409f7af36.jpg","https://user-images.githubusercontent.com/63277040/205916367-1cbf9b9d-db33-49f3-b068-f464bd5679b4.jpg","https://user-images.githubusercontent.com/63277040/205916371-1510bd6f-fbdc-481d-9964-8f4b066e6acd.jpg","일정 : 3/12(토) ~ 4/21(목)")
    )