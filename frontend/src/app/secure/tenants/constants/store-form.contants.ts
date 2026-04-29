import { SelectOption } from "src/app/shared/interfaces/select-option";

export const CURRENCIES: SelectOption[] = [
  { value: 'usd', label: 'US Dollar (USD)' },
  { value: 'eur', label: 'Euro (EUR)' },
  { value: 'gbp', label: 'British Pound (GBP)' },
  { value: 'jpy', label: 'Japanese Yen (JPY)' },
  { value: 'chf', label: 'Swiss Franc (CHF)' },
  { value: 'cad', label: 'Canadian Dollar (CAD)' },
  { value: 'aud', label: 'Australian Dollar (AUD)' },
  { value: 'cny', label: 'Chinese Yuan (CNY)' },
  { value: 'inr', label: 'Indian Rupee (INR)' },
  { value: 'brl', label: 'Brazilian Real (BRL)' },
  { value: 'bob', label: 'Boliviano (BOB)' }
];

export const DEFAULT_LANGUAGES: SelectOption[] = [
  { value: 'en', label: 'English' },
  { value: 'es', label: 'Spanish' },
  { value: 'fr', label: 'French' },
  { value: 'de', label: 'German' },
  { value: 'it', label: 'Italian' },
  { value: 'pt', label: 'Portuguese' },
  { value: 'zh', label: 'Chinese (Mandarin)' },
  { value: 'ja', label: 'Japanese' },
  { value: 'ru', label: 'Russian' },
  { value: 'ar', label: 'Arabic' }
];

export const TIMEZONES: SelectOption[] = [
  { value: 'UTC', label: 'UTC (Coordinated Universal Time)' },
  { value: 'America/New_York', label: 'UTC−05:00 — New York (Eastern Time)' },
  { value: 'America/Chicago', label: 'UTC−06:00 — Chicago (Central Time)' },
  { value: 'America/Denver', label: 'UTC−07:00 — Denver (Mountain Time)' },
  { value: 'America/Los_Angeles', label: 'UTC−08:00 — Los Angeles (Pacific Time)' },
  { value: 'Europe/London', label: 'UTC+00:00 — London' },
  { value: 'Europe/Berlin', label: 'UTC+01:00 — Berlin' },
  { value: 'Asia/Tokyo', label: 'UTC+09:00 — Tokyo' },
  { value: 'Asia/Shanghai', label: 'UTC+08:00 — Shanghai' },
  { value: 'Australia/Sydney', label: 'UTC+10:00 — Sydney' }
];