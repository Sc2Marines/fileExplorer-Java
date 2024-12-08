cd src
javac -d classes $(find | grep -E "./com/.*\.java")
java -cp classes/ com.esiea.pootd2.ExplorerApp