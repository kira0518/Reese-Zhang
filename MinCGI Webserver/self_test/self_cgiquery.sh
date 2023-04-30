dos2unix self_cgiquery_expected.out
cd ..
python3 webserv.py config.cfg &
PID=$!
cd -
sleep 0.2
result=`curl -s 127.0.0.1:8070/cgibin/hello.py?name=kira | diff - self_cgihello_expected.out`
echo $result
if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
