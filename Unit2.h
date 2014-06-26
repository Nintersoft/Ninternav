//---------------------------------------------------------------------------

#ifndef Unit2H
#define Unit2H
//---------------------------------------------------------------------------
#include <System.Classes.hpp>
#include <FMX.Controls.hpp>
#include <FMX.Forms.hpp>
#include <FMX.Edit.hpp>
#include <FMX.Header.hpp>
#include <FMX.StdCtrls.hpp>
#include <FMX.Types.hpp>
#include <FMX.WebBrowser.hpp>
#include <FMX.ActnList.hpp>
#include <FMX.Gestures.hpp>
#include <System.Actions.hpp>
//---------------------------------------------------------------------------
class TForm2 : public TForm
{
__published:	// IDE-managed Components
	THeader *Header1;
	TWebBrowser *WebBrowser1;
	TEdit *Edit1;
	TSpeedButton *SpeedButton1;
	TTimer *Timer1;
	TButton *Button1;
	TTimer *Timer2;
	TGestureManager *GestureManager1;
	TActionList *ActionList1;
	TAction *Action1;
	TAction *Action2;
	TAction *Action3;
	void __fastcall Edit1Typing(TObject *Sender);
	void __fastcall Edit1KeyDown(TObject *Sender, WORD &Key, System::WideChar &KeyChar,
          TShiftState Shift);
	void __fastcall Edit1Exit(TObject *Sender);
	void __fastcall SpeedButton1Click(TObject *Sender);
	void __fastcall Timer1Timer(TObject *Sender);
	void __fastcall Button1Click(TObject *Sender);
	void __fastcall Timer2Timer(TObject *Sender);
	void __fastcall WebBrowser1DidFinishLoad(TObject *ASender);
	void __fastcall WebBrowser1DidFailLoadWithError(TObject *ASender);
	void __fastcall Action1Execute(TObject *Sender);
	void __fastcall Action2Execute(TObject *Sender);
	void __fastcall Action3Execute(TObject *Sender);

private:	// User declarations
public:		// User declarations
	__fastcall TForm2(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm2 *Form2;
//---------------------------------------------------------------------------
#endif
