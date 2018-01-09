import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CsrAppModule } from './app.module';

//ProdConfig();

if (module['hot']) {
    module['hot'].accept();
}

platformBrowserDynamic().bootstrapModule(CsrAppModule)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));
