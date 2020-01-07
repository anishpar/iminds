import { Component } from '@angular/core';
import { language } from '../../../../language/base.lang';
import { stlConfigurations } from '../../../../config/config';
import { AuthService } from '../services/auth.service';
import { ServiceLocator } from '../../util/services/locator.service';
import { Subject } from 'rxjs/internal/Subject';
import { Angular2Csv } from '../../util/services/angular2csv.service';
import { DataStoreService } from '../../util/services/data-store.service';

@Component({
  selector: 'stl-master',
  template: ''
})
export class MasterComponent {
  public lang;
  public config;
  public onDestroy$: Subject<void> = new Subject();

  _auth: AuthService;
  dataStore: DataStoreService;

  constructor() {
    if (ServiceLocator.injector) {
      this._auth = ServiceLocator.injector.get(AuthService);
      this.dataStore = ServiceLocator.injector.get(DataStoreService);
    }

    this.lang = language;
    this.config = stlConfigurations;

  }

  setNavParam(navModule:string, val) {
    this.dataStore.setData(navModule+'NAV',val);
  }

  getNavParam(navModule:string) {
    return this.dataStore.getData(navModule+'NAV');
  }

  dateToServerFormat(date: Date) {
    const dd = date.getDate();
    const mm = date.getMonth() + 1;
    const yy = date.getFullYear();
    const final_date = yy + '-' + (mm < 10 ? '0' + mm : mm) + '-' + (dd < 10 ? '0' + dd : dd);
    return final_date;
  }

  dateToCSVFormat(date: Date) {
    const dd = date.getDate();
    const mm = date.getMonth() + 1;
    const yy = date.getFullYear();
    const final_date = (dd < 10 ? '0' + dd : dd) + '/' + (mm < 10 ? '0' + mm : mm) + '/' + yy;
    return final_date;
  }


  customDateFormat(date: Date, format) {
    const dd = date.getDate();
    const mm = date.getMonth() + 1;
    const yy = date.getFullYear();
    const final_date = (dd < 10 ? '0' + dd : dd) + format + (mm < 10 ? '0' + mm : mm) + format + yy;
    return final_date;
  }


  dateTimeToCSVFormat(date: Date) {
    const dd = date.getDate();
    const mm = date.getMonth() + 1;
    const yy = date.getFullYear();
    const hh = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    const final_date = (dd < 10 ? '0' + dd : dd) + '/' + (mm < 10 ? '0' + mm : mm) + '/' + yy
      + ' ' + (hh < 10 ? '0' + hh : hh)
      + ':' + (min < 10 ? '0' + min : min) + ':' + (sec < 10 ? '0' + sec : sec);
    return final_date;
  }

  downloadFile(fileData, filename) {

    const url = window.URL.createObjectURL(fileData);
    if (filename.indexOf('csv') === -1) {
      /*** MSO v1.0_20180331 - IE file download issue fix - start ***/
      if (navigator.appVersion.toString().indexOf('.NET') > 0) {
        window.navigator.msSaveBlob(fileData, filename);
      } else {
        /*** MSO v1.0_20180331 - IE file download issue fix - end ***/
        window.open(url);
      }
    } else {
      /*** MSO v1.0_20180331 - IE file download issue fix - start ***/
      if (navigator.appVersion.toString().indexOf('.NET') > 0) {
        window.navigator.msSaveBlob(fileData, filename);
      } else {
        /*** MSO v1.0_20180331 - IE file download issue fix - end ***/
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('visibility', 'hidden');

        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }
  }


  /**
   * Returns Date object from string of format YYYY-MM-DD
   * @param dateString string of format YYYY-MM-DD
   */
  fromStringToDate(dateString: string) {
    const from =  dateString.split('-');
    if (from && from.length > 2) {
      return new Date(Number(from[0]), Number(from[1]) - 1, Number(from[2]));
    } else {
      return new Date();
    }
  }

  // Removing/Escape HTML tags
  strip_tags(str, allow) {
    // making sure the allow arg is a string containing only tags in lowercase (<a><b><c>)
    allow = (((allow || '') + '').toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join('');
    const tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi;
    const commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
    return str.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
      return allow.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
    });
  }


  assignDefaultValue(list, keyValue, compareKey = 'alias', returnModelKey = 'id') {

    const value = this.config.defaultValues[keyValue];
    if (list) {
      const getData = list.filter( (entry) => {
        return entry[compareKey] == value;
      });
      return getData[0] && getData[0][returnModelKey] ? getData[0][returnModelKey] : '';
    } else {
      return '';
    }
  }

   /**
   * Returns date object from date sting of dd/mm/yyyy format
   * @param dateString string of dd/mm/yyyy format
   */
  dateFromString(dateString: string) {
    const dateParts = dateString.split('/');

    // month is 0-based, that's why we need dataParts[1] - 1
    return new Date(+dateParts[2], (Number(dateParts[1]) - 1), +dateParts[0]);
  }

   number_format (number, decimals, dec_point, thousands_sep) {
      // Strip all characters but numerical ones.
      number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
      let n = !isFinite(+number) ? 0 : +number,
          prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
          sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
          dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
          s,
          toFixedFix = function (n, prec) {
              const k = Math.pow(10, prec);
              return '' + Math.round(n * k) / k;
          };
      // Fix for IE parseFloat(0.55).toFixed(0) = 0;
      s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
      if (s[0].length > 3) {
          s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
      }
      if ((s[1] || '').length < prec) {
          s[1] = s[1] || '';
          s[1] += new Array(prec - s[1].length + 1).join('0');
      }
      return s.join(dec);
  }

  exportCSV(csv_data, filename = 'download') {
    const csv = new Angular2Csv(csv_data, filename);
  }

   // index refreshting
   commonTrackByFn(item) {
    return item.id;
  }
   commonTrackByIndex(index) {
    return index;
  }

  manageDestroy() {
    this.onDestroy$.next();
    this.onDestroy$.complete();
    this._auth._service.activeCalls = 0;
  }

}
