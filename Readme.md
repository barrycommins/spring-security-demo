# Spring Security Demo

This was just a little demo to figure out a problem I was having at work.

I was trying to find a way of applying separate HTTP security configurations to two sets of endpoints.

I had tried creating multiple `WebSecurityConfigurerAdapter`s, but they keep stepping on each others toes.

I also needed the solution to be extensible by other developers (it was part of a Spring Boot starter).


Eventually the solution was to create multiple instances of `SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity>`
and use `http.apply(securityConfigurer)` for each on the `WebSecurityConfigurerAdapter`


The scenario in this code could easily be simplified to just provide one `WebSecurityConfigurer` with both (or all) paths,
but it was only intended to prove and then solve the more complex real problem.