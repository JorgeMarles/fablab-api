package com.example.demo.auth;

public class JwtAuthorizationFilter  {
/*
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper mapper, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null ) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null && jwtUtil.validateClaims(claims)){
                String email = claims.getSubject();
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e){
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details",e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getWriter(), errorDetails);

        }
        
        filterChain.doFilter(request, response);
		
	}
    */
}