cd ..
python3 webserv.py config.cfg &
PID=$!
cd -
result=python3 ../webserv.py | diff - missing_arg.out
kill $PID
