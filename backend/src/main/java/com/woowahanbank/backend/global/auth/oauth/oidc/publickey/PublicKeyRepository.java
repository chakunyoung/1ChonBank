package com.woowahanbank.backend.global.auth.oauth.oidc.publickey;

import org.springframework.data.repository.CrudRepository;

public interface PublicKeyRepository extends CrudRepository<PublicKeyDto, String> {

}
