import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { KeycloakService, KeycloakEvent, KeycloakEventType } from 'keycloak-angular';

@Injectable({
    providedIn: 'root'
})
export class StartupService {

    private _startupData: any;
    private _configuration: any;
    public enabledSSO = false;

    constructor(private http: HttpClient, public keycloak: KeycloakService) { }
    // constructor() { }

    load(): Promise<any> {

        this._startupData = null;
		console.log('fetching config.json');
        return this.http
            .get('/iminds/STL/stlconfig/config.json')
            // .get('/stlconfig/config.json')
            .toPromise()
            .then((data: any) => {
                this._startupData = data;
				console.log('got config.json::' + this.startupData['api_host']);
                let apiURL = '';
                //apiURL = this.startupData['api_host'] + 'loadKeyCloakConfiguration';
				apiURL = '/iminds/loadKeyCloakConfiguration';
                // apiURL = '/assets/json/load_app_configurations.json';

                return this.http.get(apiURL).toPromise()
                .then(configuration => {
                    this._configuration = configuration && configuration['responseObject'] ? configuration['responseObject'] : {};

                    if (this._configuration && this._configuration['isSSOEnabled'] === 'true') {
                        this.enabledSSO = true;
                        return new Promise( async ( resolve, reject ) => {
                            try {
                                this.keycloak.keycloakEvents$.subscribe(event => {
                                    if (event.type == KeycloakEventType.OnAuthLogout || event.type == KeycloakEventType.OnTokenExpired
                                         || event.type == KeycloakEventType.OnAuthError
                                         || event.type == KeycloakEventType.OnAuthRefreshError
                                         ) {
                                        this.keycloak.logout();
                                    }
                                  });
                                await this.keycloak.init( {
                                    config: {
                                        'realm': this._configuration['realm'] || '',
                                        'url': this._configuration['authServerURL'] || '',
                                        'clientId': this._configuration['clientId'] || '',
                                        'credentials': {
                                            'secret': this._configuration['secret'] || ''
                                        }
                                    },

                                    authorizationHeaderName: 'SSO-AUTH-TOKEN',
                                    loadUserProfileAtStartUp: false,
                                    enableBearerInterceptor: false
                                } );
                                resolve();
                            } catch ( error ) {
                                reject( error );
                            }
                        } );
                    } else {
                        return Promise.resolve(1);
                    }
                    // return configuration;
                });
            })
            .catch((err: any) => Promise.resolve());
    }

    get startupData(): any {
        return this._startupData;
    }
    get configuration(): any {
        return this._configuration;
    }
    public logoutSSO(redirect = true) {
        this.keycloak.logout(redirect ? this._configuration['redirectUrl'] : '');
    }
}
