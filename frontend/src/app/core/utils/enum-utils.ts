import { SelectOption } from "src/app/shared/interfaces/select-option";

export function enumToSelectOptions<T extends object>(enumObj: T): SelectOption[] {
  return Object.keys(enumObj)
    .filter(key => isNaN(Number(key)))
    .map(key => ({
      label: key,
      value: (enumObj as any)[key]
    }));
}