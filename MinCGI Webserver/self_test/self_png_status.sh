dos2unix self_png_status_expected.out
cd ..
python3 webserv.py config.cfg &
PID=$!
cd -
sleep 0.2s
result=`curl -s -I 127.0.0.1:8070/megumi.png | diff - self_png_status_expected.out`
echo $result
if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
