# Bigdataworkshop
Workshop on spark for geek night 31st March

Description of parameters :-

1. user-name  : User name of the linux user with whom you want to run job
2. password   : Password of the user
3. class      : Fully qualified name of the class, for example com.tw.example.MRExample
4. args       : arguments needed by class main driver

For example to run SparkExample - Word Count
`docker run -v <project_dir_path>:/local/git  achalag/geeknight-spark  <mvn_command>`

Mvn Command
`mvn clean deploy -Dusername=<user-name> -Dpassword=<password> -Dclass=com.tw.example.SparkExample -Dargs="<input-dir> <output-dir>"`

For example
`docker run -v /user/someuser/projects/bigdataworkshop:/local/git achalag/geeknight-spark mvn clean deploy -Dusername=username -Dpassword=somepassword -Dclass=com.tw.example.SparkExample -Dargs="/home/tw/data/tweets /home/tw/data/username/job1"`

**Note**: Don't forget to delete output dir before running a Spark job.

Some web interfaces to check logs :
Spark Master Web UI	http://10.133.124.48:8080/
FS Web UI	http://10.133.124.48:2222/
