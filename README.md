# week-4-FurkanAr
week-4-FurkanAr created by GitHub Classroom

Sistem bütün çağrıların tek bir yerden yönlendirildiği api gateway üzerinden çalışmakta. Ayrıca derslerde ölçekleme amacıyla kullandığımız loadbalancer sayesinde 
sistemde yaşanan yoğunluklarda emlakcepte-service den bir kaç tane daha yaratarak servislerin hizmet alanını genişletmiştik. 

İlk olarak kullanıcıdan kayıt bilgisi istenmektedir, bu sayede ilan yayınlayabilir, arama yapabilir ve paket alabilmektedir. 

Kullanıcı sisteme kayıt olduğunda bir kuyruk oluşur ve notification servisine kullanıcının bilgileri gönderilir. Notification servis gelen kullanıcı bilgisini 
kuyrukdan okur ve veritabanına kaydeder. 

EmlakCepte uygulamasında Kullanıcı kayıt olduktan sonra paket sahibi olarak 10 tane ilan yayınlayabilmektedir. Paketlerini yenilediği takdirde 
ilan yayınlamaya devam etmektedir. Kullanıcı sistemden paket aldıkça ilan yayınlayabilmektedir bunda bir kısıtlama yoktur, sadece kayıt olurken belli 
bir "cash" yani para bilgisi istenmektedir. Paketin fiyatı 50 birimdir eğer kullanıcının parası 50 birimden az ise paket alamamaktadır. 

Kullanıcı bir paket aldığında aldığı paket ve kullanıcı bilgisi payment servisine senkron olarak gider. Payment Servis bir feign client olarak çalışmaktadır. 
Eğer ödeme işlemi başarılıysa alınan paket ve ödeme bilgisi ekrana döndürülür. Payment servisine gelen ödeme bilgileri de kendi veritabanına kaydeder.
Ödeme bilgisi kuyruğa atılır, emlakcepte servisi gelen kuyruk bilgisini okur ve kullancının paketi tanımlanır. Bütün bilgiler emlakcepte veritabanına kaydedilir.

Kullanıcı ilanının yayınladığında IN_REVIEW statüsünde yayınlanmaktadır. Yayınlanan ilan bilgisi kuyruğa atılır kuyruktan gelen ilanın bilgisini emlakcepte
servisinde bulunan realtystatus servisi okur ve ilanın statüsünü Active olarak değiştirir. 

Projenin mikroservis diyagramı ve entity relationship diyagramı da eklenmekmiştir. Ek olarak postman den yapılan api istekleri de collection.json formatında eklenmiştir.


