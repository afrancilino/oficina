package com.francilino.oficina.security;

import com.francilino.oficina.log.ApplicationListenerBean;
import com.francilino.oficina.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //INJETANDO DEPENDÊNCIA
    @Autowired
    private LoginService userDetailsService;
    
    //HABILITANDO LOG
    @Bean
    public ApplicationListenerBean eventListenerBean() {
        return new ApplicationListenerBean();
    }
    
    //AUTORIZANDO O LOGIN
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //LIBERAÇÃO DE TODO CONTEÚDO DA PASTA RESOURCES
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/documentos/**", "/imagens/**", "/js/**", "/materialize/**");
    }

    //CONFIGURAÇÃO DE ACESSO E SEGURANÇA DA APLICAÇÃO
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //HABILITAÇÃO DE TOKEN CSRF (((NÃO ESQUECER DE HABILITAR QUANDO COMEÇAR A CONSTRUIR AS PÁGINAS)))
        http.csrf().disable().authorizeRequests();

        //PÁGINA PARA ACESSO NÃO PERMITIDO DE ACORDO COM SUAS PERMISSÕES
        http.exceptionHandling().accessDeniedPage("/negado");

        //QUANDO HOUVER ERRO DE AUTENTICAÇÃO POR SESSÃO EXPIRADA
        http.sessionManagement().sessionAuthenticationErrorUrl("/erro");
        http.sessionManagement().invalidSessionUrl("/");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.sessionManagement().maximumSessions(1);
        http.sessionManagement().sessionFixation().migrateSession();

        //CONFIGURAÇÃO DOS COOKIES
        http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
        http.headers().httpStrictTransportSecurity().maxAgeInSeconds(63072000).includeSubDomains(true);
        http/*.requiresChannel().antMatchers("/*").requiresSecure()
                .and()*/.authorizeRequests()/*.anyRequest().permitAll();*/
                .antMatchers(HttpMethod.GET, "/", "/index", "/erro", "/negado", "/construcao", "/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
