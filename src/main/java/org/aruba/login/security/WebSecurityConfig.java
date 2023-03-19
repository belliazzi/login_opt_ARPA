package org.aruba.login.security;

import javax.sql.DataSource;

import org.aruba.login.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Questa classe rappresenta la configurazione di sicurezza per un'applicazione web basata su Spring Security. La classe
 * estende la classe WebSecurityConfigurerAdapter per fornire un'implementazione personalizzata della configurazione di
 * sicurezza. L'annotazione @Configuration indica che la classe definisce una o più configurazioni Spring.
 * L'annotazione @EnableWebSecurity attiva la configurazione di sicurezza basata su Spring Security.
 * 
 * @author damiano
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;

    /**
     * Il metodo userDetailsService() definisce il servizio per la gestione degli utenti personalizzato, ovvero la
     * classe CustomUserDetailsService. Il metodo passwordEncoder() definisce l'encoder di password bcrypt utilizzato
     * per la codifica delle password degli utenti.
     */
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailsService();
    }

    /**
     * Il metodo restituisce un'istanza di BCryptPasswordEncoder, una classe di Spring Security che implementa
     * l'interfaccia PasswordEncoder per la codifica di password.
     * 
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * Il metodo authenticationProvider() definisce il provider di autenticazione che utilizza il servizio di gestione
     * degli utenti personalizzato e l'encoder di password.
     * 
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Il metodo configure(AuthenticationManagerBuilder auth) imposta il provider di autenticazione definito in
     * precedenza per il manager di autenticazione.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Il metodo configure(HttpSecurity http) imposta le regole di sicurezza per le richieste HTTP dell'applicazione. In
     * particolare, viene definita la restrizione per l'accesso alla risorsa "/users", consentendo l'accesso solo agli
     * utenti autenticati. Viene inoltre definita la configurazione del form di login e di logout dell'applicazione.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/users").authenticated().anyRequest().permitAll().and().formLogin()
            .usernameParameter("email").defaultSuccessUrl("/users").permitAll().and().logout().logoutSuccessUrl("/")
            .permitAll();
    }

}
