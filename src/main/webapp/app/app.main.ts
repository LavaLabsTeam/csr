import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CsrAppModule } from './app.module';

import { enableProdMode } from '@angular/core';
import { DEBUG_INFO_ENABLED } from './app.constants';

if (!DEBUG_INFO_ENABLED) {
    enableProdMode();
}

if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic().bootstrapModule(CsrAppModule)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
