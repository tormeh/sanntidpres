%% -module(example1). %% AUTO-FIX
-compile(export_all).
-import(timer, [sleep/1]).
-uncoverable("trueLovelabel>0").
-uncoverable("noTrueLovelabel>0").

main() ->
  io:format("lol\n"),
  Pidth1 = spawn(fun() -> the() end),
  Pidth2 = spawn(fun() -> tho() end),
  Pidth1 ! {Pidth2, cupidsArrow},
  sleep(2000),
  ok.

the()->
	receive
	  {To, cupidsArrow} ->
	    To ! {self(), unrequitedLove},
	    io:format("lol2\n");
	  {From, trueLove} ->
      	?label("trueLovelabel"),
	    io:format("Found true love!\n")
	end.

tho()->
	receive
	  {From, unrequitedLove} ->
      	?label("noTrueLovelabel"),
	    io:format("Not interested\n")
	end.
