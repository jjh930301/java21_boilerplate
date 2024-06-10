package com.app.api.global.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.app.api.global.model.ApiRes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// @SecurityRequirement(name="bearerAuth")
@Operation
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200" , 
        content= @Content(
            schema = @Schema(
                implementation = ApiRes.class
            )
        )
    )
})
public @interface SwaggerInfo {
    String summary() default "";

    String description() default "";

    boolean deprecated() default false;

    Class<?> implementation() default ApiRes.class;

    String[] securityRequirements() default {};
}
