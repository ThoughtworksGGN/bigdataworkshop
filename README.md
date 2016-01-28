# Bigdataworkshop
Workshop on crunch and mapreduce for geek night 28th jan

Download Dependencies:
`mvn clean package`

To run hadoop job use following command :

`mvn clean deploy -Dusername=<user-name> -Dpassword=<password> -Phadoop  -Dclass=<class> -Dargs=<args>`

To run spark job use following command :

`mvn clean deploy -Dusername=<user-name> -Dpassword=<password> -Pspark  -Dclass=<class> -Dargs=<args>`

Description of parameters :-

1. user-name  : User name of the linux user with whom you want to run job
2. password   : Password of the user
3. class      : Fully qualified name of the class, for example com.tw.example.MRExample
4. args       : arguments needed by class main driver

For example to run MRExample - Word Count

`mvn clean deploy -Dusername=<user-name> -Dpassword=<password> -Phadoop  -Dclass=com.tw.example.MRExample -Dargs="<input-dir> <output-dir>"`

For example to run SparkExample - Word Count

`mvn clean deploy -Dusername=<user-name> -Dpassword=<password> -Pspark  -Dclass=com.tw.example.SparkExample -Dargs="<input-dir> <output-dir>"`

**Note**: Don't forget to delete output dir before running a MR/Spark job. 

Some web interfaces to check logs :
Spark Master Web UI	http://10.133.125.251:8080/
File browser				http://10.133.125.251:50070/explorer.html#/
Application Master	http://10.133.125.251:8088/cluster
Job History server	http://10.133.125.251:19888/jobhistory
