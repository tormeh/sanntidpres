%% -module(example1). %% AUTO-FIX
-compile(export_all).
-import(timer, [sleep/1]).
-uncoverable("trueLovelabel>0").
-uncoverable("noTrueLovelabel>0").

main() ->
  Pidthe = spawn(fun() -> the() end),
  Pidtho = spawn(fun() -> tho() end),
  Pidthe ! {Pidtho, cupidsArrow},
  sleep(2000),
  ok.

the()->
	receive
	  {To, cupidsArrow} ->
	    To ! {self(), unrequitedLove};
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
