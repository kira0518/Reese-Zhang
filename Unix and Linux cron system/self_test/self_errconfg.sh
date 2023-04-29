cd ..
dos2unix self_errorcfg_expected.out
python3 webserv.py config.cfg &
PID=$!

sleep 0.2
result=`python3 webserv.py self_errorcfg.cfg | diff - self_errorcfg_expected.out`
echo $result
echo `python3 webserv.py self_errorcfg.cfg`
cat self_errorcfg_expected.out

if [ "$result" == "" ]
then
    echo "pass"
else
    echo "fail"
fi

pkill python
