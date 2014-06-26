//---------------------------------------------------------------------------

#ifndef Unit1H
#define Unit1H
//---------------------------------------------------------------------------
#include <System.Classes.hpp>
#include <FMX.Controls.hpp>
#include <FMX.Forms.hpp>
#include <FMX.Edit.hpp>
#include <FMX.Header.hpp>
#include <FMX.Layouts.hpp>
#include <FMX.StdCtrls.hpp>
#include <FMX.Types.hpp>
#include <FMX.ActnList.hpp>
#include <FMX.Gestures.hpp>
#include <System.Actions.hpp>
//---------------------------------------------------------------------------
class TForm1 : public TForm
{
__published:	// IDE-managed Components
	THeader *Header1;
	TFramedScrollBox *FramedScrollBox1;
	TLabel *Label1;
	TSpeedButton *SpeedButton1;
	TEdit *Edit1;
	TSpeedButton *SpeedButton2;
	TTimer *Timer1;
	TButton *Button1;
	TGestureManager *GestureManager1;
	TActionList *ActionList1;
	TAction *Action1;
	void __fastcall SpeedButton2Click(TObject *Sender);
	void __fastcall Edit1KeyDown(TObject *Sender, WORD &Key, System::WideChar &KeyChar,
          TShiftState Shift);
	void __fastcall Timer1Timer(TObject *Sender);
	void __fastcall SpeedButton1Click(TObject *Sender);
	void __fastcall FormCreate(TObject *Sender);
	void __fastcall FormResize(TObject *Sender);
	void __fastcall Button1Click(TObject *Sender);
	void __fastcall Action1Execute(TObject *Sender);
private:	// User declarations
public:		// User declarations
	__fastcall TForm1(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm1 *Form1;
//---------------------------------------------------------------------------
#endif
