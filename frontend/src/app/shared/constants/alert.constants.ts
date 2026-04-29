import {AlertTypes} from 'src/app/core/enums/alert-types.enum';

export const ALERT_ICON_MAP: Record<AlertTypes, string> = {
  [AlertTypes.Success]: 'icon-check-mark-bold-filled',
  [AlertTypes.Warning]: 'icon-warning-filled',
  [AlertTypes.Error]: 'icon-close-bold-filled',
  [AlertTypes.Info]: 'icon-note-filled'
};
