import { HttpSession } from './http.session';

export class HttpConfig {
  private static readonly _session: HttpSession = new HttpSession();

  public static getSession(): HttpSession {
    return this._session;
  }
}
